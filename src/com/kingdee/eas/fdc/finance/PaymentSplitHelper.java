package com.kingdee.eas.fdc.finance;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.util.FDCSplitHelper;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.IContractSettlementBill;
import com.kingdee.eas.fdc.contract.ISettlementCostSplit;
import com.kingdee.eas.fdc.contract.SettNoCostSplitCollection;
import com.kingdee.eas.fdc.contract.SettNoCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.SettNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitCollection;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 添加此类统一处理与付款拆分相关的逻辑，包括界面以及后台
 * @author xiaohong_shi 
 * 2009/1/16
 */
public class PaymentSplitHelper {
	
	/**
	 * 成本科目合同付款拆分
	 */
	public static final int PAYMENTSPLIT = 1;

	/**
	 * 成本科目无文本合同付款拆分
	 */
	public static final int PAYMENTSPLIT_WITHOUTTEXT = 2;

	/**
	 * 财务科目（非成本科目）合同付款拆分
	 */
	public static final int PAYMENTNOCOSTSPLIT = 3;

	/**
	 * 财务科目（非成本科目）无文本合同付款拆分
	 */
	public static final int PAYMENTNOCOSTSPLIT_WITHOUTTEXT = 4;
	
	/**
	 * 调整模式下处理已结算的结算拆分成本金额,已拆分成本金额,已拆分付款金额,已拆分保修款金额等
	 * 处理所有模式下结算拆分前的拆分，这时候所有模式都只有进度款，处理时一致的
	 * 增加处理多次结算
	 *  by sxhong 2009-07-30 10:42:05
	 * 
	 * @throws Exception
	 */
	public static void handleCostAdjustModelSplitedAmt(Context ctx,PaymentSplitInfo paymentSplitInfo,String contractId) throws BOSException,EASBizException{
		if(contractId==null){
			contractId=paymentSplitInfo.getPaymentBill().getContractBillId();
		}
		if(contractId==null){
			return;
		}
		//1.合同未结算及合同最终结算未拆分，此时的成本金额为合同加变更
		//2.合同结算未最终结算，成本金额=max(结算拆分金额,合同加变更)
		//3.合同最终结算且拆分，成本金额=结算拆分金额
		boolean isNoSettle=false;
		//结算拆分分录
		Map settleSplitEntryMap=new HashMap();
		//以前的付款拆分分录
		Map paySplitEntryMap=new HashMap();
		//已拆分的保修款付款拆分分录
//		Map paySplitGrtEntryMap=new HashMap();
		//多次结算
		boolean isMoreSetter = FDCUtils.getDefaultFDCParamByKey(ctx, null, FDCConstants.FDC_PARAM_MORESETTER);
		//结算拆分总金额
		BigDecimal settleSplitAmt = FDCHelper.ZERO;
		//合同+变更
		BigDecimal conAndChangeSplitAmt = FDCHelper.ZERO;
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractId));
		IContractSettlementBill icontractsettlementBill = null;
		if(ctx!=null){
			icontractsettlementBill=ContractSettlementBillFactory.getLocalInstance(ctx);
			
		}else{
			icontractsettlementBill=ContractSettlementBillFactory.getRemoteInstance();
		}
		//是否存在结算单（包括未最终结算及最终结算）
		boolean isSettle = icontractsettlementBill.exists(filter);
		
		filter.getFilterItems().add(new FilterItemInfo("isFinalSettle", Boolean.TRUE, CompareType.EQUALS));
		boolean isFinalSettle = icontractsettlementBill.exists(filter);
		
		
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
		if(isMoreSetter&&!isFinalSettle){
//			filter.getFilterItems().add(new FilterItemInfo("settlementBill.isSettled", Boolean.TRUE, CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("settlementBill.isFinalSettle", Boolean.FALSE, CompareType.EQUALS));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("settlementBill.isFinalSettle", Boolean.TRUE, CompareType.EQUALS));
		}
		filter.getFilterItems().add(new FilterItemInfo("settlementBill.contractBill.id", contractId, CompareType.EQUALS));
		
		view.getSelector().add("entrys.amount");
		view.getSelector().add("entrys.grtSplitAmt");
		view.getSelector().add("entrys.grtSplitAmt");
		view.getSelector().add("entrys.product.id");
		view.getSelector().add("entrys.costAccount.id");
		view.getSelector().add("entrys.costBillId");
		view.getSelector().add("entrys.splitType");
		view.setFilter(filter);
		ISettlementCostSplit iSettlementSplit = null;
		if(ctx!=null){
			iSettlementSplit=SettlementCostSplitFactory.getLocalInstance(ctx);
		}else{
			iSettlementSplit=SettlementCostSplitFactory.getRemoteInstance();
		}
		SettlementCostSplitCollection settleSplits=iSettlementSplit.getSettlementCostSplitCollection(view);
		if(settleSplits!=null&&settleSplits.size()>0){
			for(int i=0;i<settleSplits.size();i++){
				for (Iterator iter = settleSplits.get(i).getEntrys().iterator(); iter.hasNext();) {
					SettlementCostSplitEntryInfo entry = (SettlementCostSplitEntryInfo) iter.next();
					if(entry.getCostAccount()==null){
						continue;
					}
					String key=entry.getCostAccount().getId().toString();
					key=key+entry.getCostBillId().toString();
					if(entry.getSplitType()!=null){
						key=key+entry.getSplitType().getValue();
					}
					if(entry.getProduct()!=null){
						key=key+entry.getProduct().getId().toString();
					}
					if(isMoreSetter){
						if(settleSplitEntryMap.containsKey(key)){
							SettlementCostSplitEntryInfo acctEntry = (SettlementCostSplitEntryInfo)settleSplitEntryMap.get(key);
							acctEntry.setAmount(FDCHelper.add(acctEntry.getAmount(), entry.getAmount()));
							settleSplitEntryMap.put(key, acctEntry);
						}else{
							settleSplitEntryMap.put(key, entry);
						}
						settleSplitAmt=FDCHelper.add(settleSplitAmt, entry.getAmount());
					}else{
						settleSplitEntryMap.put(key, entry);
					}
				}
				
			}
		}else{
			if(isMoreSetter&&isSettle){
			}else{
				isNoSettle=true;
			}
		}
		//所有之前的付款拆分
		view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("contractBill.id", contractId);
		view.getFilter().getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
		if(paymentSplitInfo!=null&&paymentSplitInfo.getId()!=null){
			view.getFilter().getFilterItems().add(new FilterItemInfo("id", paymentSplitInfo.getId().toString(), CompareType.NOTEQUALS));
		}
		/*if(paymentSplitInfo.getCreateTime()!=null){
			view.getFilter().getFilterItems().add(new FilterItemInfo("createTime", paymentSplitInfo.getCreateTime(), CompareType.LESS));
		}*/
		//update by renliang
		if(paymentSplitInfo!=null && paymentSplitInfo.getCreateTime()!=null){
			view.getFilter().getFilterItems().add(new FilterItemInfo("createTime", paymentSplitInfo.getCreateTime(), CompareType.LESS));
		}
		view.getSelector().add("entrys.amount");
		view.getSelector().add("entrys.payedAmt");
		view.getSelector().add("entrys.invoiceAmt");
		view.getSelector().add("entrys.product.id");
		view.getSelector().add("entrys.costAccount.id");
		view.getSelector().add("entrys.costBillId");
		view.getSelector().add("entrys.splitType");
		view.getSelector().add("entrys.parent.paymentBill.fdcPayType.payType.id");
		
		IPaymentSplit iPaySplit = null;
		if(ctx!=null){
			iPaySplit = PaymentSplitFactory.getLocalInstance(ctx);
		}else{
			iPaySplit=PaymentSplitFactory.getRemoteInstance();
		}
		PaymentSplitCollection paymentSplitCollection = iPaySplit.getPaymentSplitCollection(view);
		for(int i=0;i<paymentSplitCollection.size();i++){
			for (Iterator iter = paymentSplitCollection.get(i).getEntrys().iterator(); iter.hasNext();) {
				PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) iter.next();
				if(entry.getCostAccount()==null){
					continue;
				}
				String key=entry.getCostAccount().getId().toString();
				key=key+entry.getCostBillId().toString();
				if(entry.getSplitType()!=null){
					key=key+entry.getSplitType().getValue();
				}
				if(entry.getProduct()!=null){
					key=key+entry.getProduct().getId().toString();
				}
				PaymentSplitEntryInfo mapEntry=(PaymentSplitEntryInfo)paySplitEntryMap.get(key);
				if(mapEntry==null){
					mapEntry=new PaymentSplitEntryInfo();
					paySplitEntryMap.put(key, mapEntry);
				}
				mapEntry.setAmount(FDCHelper.add(mapEntry.getAmount(), entry.getAmount()));
				mapEntry.setPayedAmt(FDCHelper.add(mapEntry.getPayedAmt(), entry.getPayedAmt()));
				mapEntry.setInvoiceAmt(FDCHelper.add(mapEntry.getInvoiceAmt(), entry.getInvoiceAmt()));
				//TODO 为什么只有保修款时才显示已拆分的保修款
				if(entry.getParent().getPaymentBill()!=null&&entry.getParent().getPaymentBill().getFdcPayType()!=null
						&&entry.getParent().getPaymentBill().getFdcPayType().getPayType()!=null
						&&entry.getParent().getPaymentBill().getFdcPayType().getPayType().getId().equals(PaymentTypeInfo.keepID)){
					mapEntry.setSplitQualityAmt(FDCHelper.add(mapEntry.getSplitQualityAmt(), entry.getPayedAmt()));
				}
					
			}
		}
		for(int i=0;i<paymentSplitInfo.getEntrys().size();i++){
			PaymentSplitEntryInfo entry = paymentSplitInfo.getEntrys().get(i);
			if(entry.getCostAccount()==null){
				continue;
			}
			conAndChangeSplitAmt=FDCHelper.add(conAndChangeSplitAmt, FDCHelper.add(entry.getContractAmt(), entry.getChangeAmt()));
		}
		for(int i=0;i<paymentSplitInfo.getEntrys().size();i++){
			PaymentSplitEntryInfo entry = paymentSplitInfo.getEntrys().get(i);
			if(entry.getCostAccount()==null){
				continue;
			}
			String key=entry.getCostAccount().getId().toString();
			key=key+entry.getCostBillId().toString();
			if(entry.getSplitType()!=null){
				key=key+entry.getSplitType().getValue();
			}
			if(entry.getProduct()!=null){
				key=key+entry.getProduct().getId().toString();
			}
			SettlementCostSplitEntryInfo settleEntry=(SettlementCostSplitEntryInfo)settleSplitEntryMap.get(key);
			PaymentSplitEntryInfo paySplitEntry=(PaymentSplitEntryInfo)paySplitEntryMap.get(key);
//			PaymentSplitEntryInfo paySplitGrtEntry=(PaymentSplitEntryInfo)paySplitGrtEntryMap.get(key);
			//成本拆分金额
			if(isNoSettle){
				//成本金额=合同加变更
				entry.setCostAmt(FDCHelper.add(entry.getContractAmt(), entry.getChangeAmt()));
				//应付进度款=成本金额
				entry.setShouldPayAmt(entry.getCostAmt());
			}else{
				BigDecimal costAmt = settleEntry!=null?settleEntry.getAmount():FDCHelper.ZERO;
				if(isMoreSetter){
					//多次结算时，结算拆分或合同加变更取大的一方
					if(settleSplitAmt.compareTo(conAndChangeSplitAmt)==-1){
						costAmt = FDCHelper.add(entry.getContractAmt(), entry.getChangeAmt());
					}
				}
				entry.setCostAmt(costAmt);
				//保修款拆分金额
//				entry.setQualityAmount(settleEntry.getGrtSplitAmt());
				//应付进度款=成本拆分金额-保修款拆分金额
				entry.setShouldPayAmt(settleEntry!=null?FDCNumberHelper.subtract(settleEntry.getAmount(), settleEntry.getGrtSplitAmt()):FDCHelper.ZERO);
			}
			
			//已拆分成本金额
			entry.setSplitedCostAmt(paySplitEntry!=null?paySplitEntry.getAmount():FDCHelper.ZERO);
			//已拆分付款金额实际为已拆分进度款金额不包括保修款部分所以要减去保修款的拆分金额
			entry.setSplitedPayedAmt(paySplitEntry!=null?FDCHelper.subtract(paySplitEntry.getPayedAmt(), paySplitEntry.getSplitQualityAmt()):FDCHelper.ZERO);			//已拆分保修款还是用以前的逻辑,暂时不处理
			entry.setSplitedInvoiceAmt(paySplitEntry!=null?paySplitEntry.getInvoiceAmt():FDCHelper.ZERO);
			entry.setSplitQualityAmt(paySplitEntry!=null?paySplitEntry.getSplitQualityAmt():FDCHelper.ZERO);
			if(paymentSplitInfo.isIsProgress()){
				entry.setQualityAmount(settleEntry.getGrtSplitAmt());
			}
		}
	}

	/**
	 * 调整模式下处理结算拆分成本金额,已拆分成本金额,已拆分付款金额,已拆分保修款金额等
	 * @throws Exception
	 */
	public static void handleNoCostAdjustModelSplitedAmt(Context ctx,PaymentNoCostSplitInfo objectValue,String contractId) throws BOSException,EASBizException{
		if(contractId==null){
			contractId=objectValue.getPaymentBill().getContractBillId();
		}
		if(contractId==null){
			return;
		}
		//结算拆分分录
		Map settleSplitEntryMap=new HashMap();
		//以前的付款拆分分录
		Map paySplitEntryMap=new HashMap();
		//已拆分的保修款付款拆分分录
		Map paySplitGrtEntryMap=new HashMap();
		//多次结算
		boolean isMoreSetter = FDCUtils.getDefaultFDCParamByKey(ctx, null, FDCConstants.FDC_PARAM_MORESETTER);
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
		if(isMoreSetter){
			filter.getFilterItems().add(new FilterItemInfo("settlementBill.isFinalSettle", Boolean.FALSE, CompareType.EQUALS));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("settlementBill.isFinalSettle", Boolean.TRUE, CompareType.EQUALS));
		}
		filter.getFilterItems().add(new FilterItemInfo("settlementBill.contractBill.id", contractId, CompareType.EQUALS));
		
		view.getSelector().add("entrys.amount");
		view.getSelector().add("entrys.grtSplitAmt");
		view.getSelector().add("entrys.grtSplitAmt");
		view.getSelector().add("entrys.product.id");
		view.getSelector().add("entrys.accountView.id");
		view.getSelector().add("entrys.costBillId");
		view.getSelector().add("entrys.splitType");
		view.getSelector().add("entrys.curProject.id");
		view.setFilter(filter);
		SettNoCostSplitCollection c=SettNoCostSplitFactory.getLocalInstance(ctx).getSettNoCostSplitCollection(view);
		for(int i=0;i<c.size();i++){
			for (Iterator iter = c.get(i).getEntrys().iterator(); iter.hasNext();) {
				SettNoCostSplitEntryInfo entry = (SettNoCostSplitEntryInfo) iter.next();
				if(entry.getAccountView()==null){
					continue;
				}
				String key=entry.getAccountView().getId().toString();
				key=key+entry.getCurProject().getId().toString();
				key=key+entry.getCostBillId().toString();
				if(entry.getSplitType()!=null){
					key=key+entry.getSplitType().getValue();
				}
				if(entry.getProduct()!=null){
					key=key+entry.getProduct().getId().toString();
				}
				settleSplitEntryMap.put(key, entry);
			}
			
		}
		//所有之前的付款拆分
		view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("contractBill.id", contractId);
		view.getFilter().getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
		if(objectValue!=null&&objectValue.getId()!=null){
			view.getFilter().getFilterItems().add(new FilterItemInfo("id", objectValue.getId().toString(), CompareType.NOTEQUALS));
		}
		view.getSelector().add("entrys.amount");
		view.getSelector().add("entrys.payedAmt");
		view.getSelector().add("entrys.product.id");
		view.getSelector().add("entrys.accountView.id");
		view.getSelector().add("entrys.costBillId");
		view.getSelector().add("entrys.splitType");
		view.getSelector().add("entrys.curProject.id");
		view.getSelector().add("entrys.parent.paymentBill.fdcPayType.payType.id");
		
		PaymentNoCostSplitCollection paymentSplitCollection = PaymentNoCostSplitFactory.getLocalInstance(ctx).getPaymentNoCostSplitCollection(view);
		for(int i=0;i<paymentSplitCollection.size();i++){
			for (Iterator iter = paymentSplitCollection.get(i).getEntrys().iterator(); iter.hasNext();) {
				PaymentNoCostSplitEntryInfo entry = (PaymentNoCostSplitEntryInfo) iter.next();
				if(entry.getAccountView()==null){
					continue;
				}
				String key=entry.getAccountView().getId().toString();
				key=key+entry.getCurProject().getId().toString();
				key=key+entry.getCostBillId().toString();
				if(entry.getSplitType()!=null){
					key=key+entry.getSplitType().getValue();
				}
				if(entry.getProduct()!=null){
					key=key+entry.getProduct().getId().toString();
				}
				PaymentNoCostSplitEntryInfo mapEntry=(PaymentNoCostSplitEntryInfo)paySplitEntryMap.get(key);
				if(mapEntry==null){
					mapEntry=new PaymentNoCostSplitEntryInfo();
					paySplitEntryMap.put(key, mapEntry);
				}
				mapEntry.setAmount(FDCHelper.add(mapEntry.getAmount(), entry.getAmount()));
				mapEntry.setPayedAmt(FDCHelper.add(mapEntry.getPayedAmt(), entry.getPayedAmt()));
				
				if(entry.getParent().getPaymentBill().getFdcPayType()!=null
						&&entry.getParent().getPaymentBill().getFdcPayType().getPayType()!=null
						&&entry.getParent().getPaymentBill().getFdcPayType().getPayType().getId().equals(PaymentTypeInfo.keepID)){
					mapEntry.setSplitQualityAmt(FDCHelper.add(mapEntry.getSplitQualityAmt(), entry.getPayedAmt()));
				}
					
			}
		}
		
		for(int i=0;i<objectValue.getEntrys().size();i++){
			PaymentNoCostSplitEntryInfo entry =objectValue.getEntrys().get(i);
			String key=entry.getAccountView().getId().toString();
			key=key+entry.getCurProject().getId().toString();
			key=key+entry.getCostBillId().toString();
			if(entry.getSplitType()!=null){
				key=key+entry.getSplitType().getValue();
			}
			if(entry.getProduct()!=null){
				key=key+entry.getProduct().getId().toString();
			}
			SettNoCostSplitEntryInfo settleEntry=(SettNoCostSplitEntryInfo)settleSplitEntryMap.get(key);
			PaymentNoCostSplitEntryInfo paySplitEntry=(PaymentNoCostSplitEntryInfo)paySplitEntryMap.get(key);
//			PaymentNoCostSplitEntryInfo paySplitGrtEntry=(PaymentNoCostSplitEntryInfo)paySplitGrtEntryMap.get(key);
			//成本拆分金额
			entry.setCostAmt(settleEntry!=null?settleEntry.getAmount():FDCHelper.ZERO);
			//保修款拆分金额
//			entry.setQualityAmount(settleEntry.getGrtSplitAmt());
			//应付进度款=成本拆分金额-保修款拆分金额
			entry.setShouldPayAmt(settleEntry!=null?FDCNumberHelper.subtract(settleEntry.getAmount(), settleEntry.getGrtSplitAmt()):FDCHelper.ZERO);
			
			//已拆分成本金额
			entry.setSplitedCostAmt(paySplitEntry!=null?paySplitEntry.getAmount():FDCHelper.ZERO);
			//已拆分付款金额
			entry.setSplitedPayedAmt(paySplitEntry!=null?FDCHelper.subtract(paySplitEntry.getPayedAmt(), paySplitEntry.getSplitQualityAmt()):FDCHelper.ZERO);
			//已拆分保修款还是用以前的逻辑,暂时不处理
//			entry.setSplitQualityAmt(paySplitGrtEntry.getSplitQualityAmt());
			if(objectValue.isIsProgress()){
				entry.setQualityAmount(settleEntry.getGrtSplitAmt());
			}
		
		}
	}

	/**
	 * 修正归属付款金额
	 * @param allEntrys
	 * @param curEntry
	 */
	public static void adjustPayAmount(IObjectCollection allEntrys,FDCSplitBillEntryInfo curEntry){
		FDCSplitHelper.adjustAmount(allEntrys, curEntry, "payedAmt");
    }

	
	/**
	 * 描述：汇总实付款分摊金额（汇总生成非明细工程项目中附加科目的成本，其值为下级工程项目相同科目的成本之和）
	 * @param ctx
	 * @param allEntrys
	 * @param curIndex
	 *  by sxhong 2009-07-20 11:34:34
	 */
	public static void totAmountPayAddlAcct(IObjectCollection allEntrys,int curIndex){
		FDCSplitHelper.totAmountAddlAcct(allEntrys, curIndex, "payedAmt");
	}

	/**
	 * 修正保修款
	 * @param allEntrys
	 * @param curEntry
	 */
	public static void adjustQuaAmount(IObjectCollection allEntrys,FDCSplitBillEntryInfo curEntry){
		FDCSplitHelper.adjustAmount(allEntrys, curEntry, "qualityAmount");
	}

	/**
	 * 描述：汇总保修款分摊金额（汇总生成非明细工程项目中附加科目的成本，其值为下级工程项目相同科目的成本之和）
	 * @param ctx
	 * @param allEntrys
	 * @param curIndex
	 *  by sxhong 2009-07-20 11:35:28
	 */
	public static void totAmountQuaAddlAcct(IObjectCollection allEntrys,int curIndex){
		FDCSplitHelper.totAmountAddlAcct(allEntrys, curIndex, "qualityAmount");
	}
	
	/**
	 * 修正归属发票金额
	 * @param allEntrys
	 * @param curEntry
	 */
	public static void adjustInvoiceAmount(IObjectCollection allEntrys,FDCSplitBillEntryInfo curEntry){
		FDCSplitHelper.adjustAmount(allEntrys, curEntry, "invoiceAmt");
	}

	/**
	 * 描述：汇总归属发票金额分摊金额（汇总生成非明细工程项目中附加科目的成本，其值为下级工程项目相同科目的成本之和）
	 * @param ctx
	 * @param allEntrys
	 * @param curIndex
	 *  by sxhong 2009-07-20 11:35:28
	 */
	public static void totAmountInvoiceAddlAcct(IObjectCollection allEntrys,int curIndex){
		FDCSplitHelper.totAmountAddlAcct(allEntrys, curIndex, "invoiceAmt");
	}
	//与付款拆分序时薄功能一样
	//可能放的位置不合适，后续再调整 by hpw 2010-5-1
	public static void checkPaymentVouchered(String id,String contractId,Map param) throws Exception {
		PaymentSplitHelper.contractId = contractId;
		boolean isSimpleFinacial = ((Boolean)param.get("isSimpleFinancial")).booleanValue();
		boolean isAdjustVourcherModel = ((Boolean)param.get("isAdjustVourcherModel")).booleanValue();
		
		if(isSimpleFinacial){
			SelectorItemCollection selector=new SelectorItemCollection();
			selector.add("fiVouchered");
			PaymentBillInfo paymentBill=PaymentBillFactory.getRemoteInstance().getPaymentBillInfo(new ObjectUuidPK(BOSUuid.read(id)));
			if(paymentBill.isFiVouchered()){
				MsgBox.showWarning("此付款单之前已经生成了凭证，重新拆分之后需要进行手工调账！");
			}
		}
		
		// 凭证检查
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("paymentBill.id", id));
		filter.getFilterItems().add(
				new FilterItemInfo("Fivouchered", Boolean.TRUE));
		filter.getFilterItems().add(
				new FilterItemInfo("state",
						FDCBillStateEnum.INVALID_VALUE,
						CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("voucherRefer",PaySplitVoucherRefersEnum.ADJUSTBILL_VALUE,CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("voucherRefer",PaySplitVoucherRefersEnum.PAYMENTBILL_VALUE,CompareType.EQUALS));
		
		filter.setMaskString("#0 and ((#1 and #2) or (#3 or #4))");

		if (PaymentSplitHelper.getBillInterface().exists(filter)) {
			if(!isSimpleFinacial){
				MsgBox.showWarning("付款拆分已经被引用，不能执行此操作！");
				SysUtil.abort();
			}
		}
		if(!isSimpleFinacial&&isAdjustVourcherModel){
			FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
			sqlBuilder.appendSql("select pe.fid from t_fnc_fdccostvoucherentry pe left join t_fnc_paymentsplit ps on pe.fparentid=ps.fpaymentbillid where "); 
			sqlBuilder.appendParam("ps.fpaymentbillid",id);
			sqlBuilder.appendSql(" union all ");
			sqlBuilder.appendSql("select pe.fid from t_fnc_fdccostvoucherentry pe left join t_fnc_paymentnocostsplit ps on pe.fparentid=ps.fpaymentbillid where "); 
			sqlBuilder.appendParam("ps.fpaymentbillid",id);
			IRowSet rowSet = sqlBuilder.executeQuery();
			if(rowSet.size()>0){
				if(!isSimpleFinacial){
					MsgBox.showWarning("对应的付款单已经生成凭证，不能执行此操作！");
					SysUtil.abort();
				}
			}
		}
	}
	public static ICoreBase getBillInterface() throws Exception {
		// switch (getSplitType()){
		switch (getSplit()) {
		case PaymentSplitHelper.PAYMENTSPLIT:
		case PaymentSplitHelper.PAYMENTSPLIT_WITHOUTTEXT:
			return com.kingdee.eas.fdc.finance.PaymentSplitFactory
					.getRemoteInstance();

		case PaymentSplitHelper.PAYMENTNOCOSTSPLIT:
		case PaymentSplitHelper.PAYMENTNOCOSTSPLIT_WITHOUTTEXT:
			return com.kingdee.eas.fdc.finance.PaymentNoCostSplitFactory
					.getRemoteInstance();
		default:
			return com.kingdee.eas.fdc.finance.PaymentSplitFactory
					.getRemoteInstance();
		}
	}
	
	/**
	 * 判断选择行是不是无文本合同，选择多行返回false
	 * 
	 * @author sxhong Date 2006-12-1
	 * @param table
	 * @return isConWithoutTxt
	 */
	private static final BOSObjectType withoutTxtConBosType = new ContractWithoutTextInfo()
			.getBOSType();
	private static String contractId = "";
	private static int getSplit() throws Exception{
		int cost = 0;
		int noCost = 0;
		int size=1;
		boolean isConWithoutTxt = false;
		for (int i = 0; i < size; i++) {
			isConWithoutTxt = BOSUuid.read(contractId).getType().equals(
					withoutTxtConBosType);

			if (contractId == null)
				return 0;
			if (isConWithoutTxt) {
				// if(true) return PAYMENTSPLIT_WITHOUTTEXT;
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("id", contractId));
				filter.getFilterItems().add(
						new FilterItemInfo("isCostSplit", 1 + ""));
				try {
					if (ContractWithoutTextFactory.getRemoteInstance().exists(
							filter)) {
						cost++;
					} else {
						noCost++;
					}
				} catch (Exception e) {
					throw new BOSException(e);
				}
			} else {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("id", contractId));
				filter.getFilterItems().add(
						new FilterItemInfo("isCoseSplit", new Integer(1)));
				try {
					if (ContractBillFactory.getRemoteInstance().exists(filter)) {
						cost++;
					} else {
						noCost++;
					}
				} catch (Exception e) {
					throw new BOSException(e);
				}
			}
		}
		if (cost == size) {
			return 1;
		} else if (noCost == size) {
			return 3;
		} else if (size > 0) {
			MsgBox.showWarning("不能同时处理成本拆分和非成本拆分，请重新选择！");
			SysUtil.abort();
			return 0;
		} else {
			SysUtil.abort();
			return 0;
		}
	}
	
}
