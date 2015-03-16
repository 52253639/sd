package com.kingdee.eas.fdc.finance.app.voucher;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillInfo;
import com.kingdee.eas.fdc.finance.WorkLoadPayVoucherEntryCollection;
import com.kingdee.eas.fdc.finance.WorkLoadPayVoucherEntryInfo;
import com.kingdee.eas.fi.cas.PaymentBillInfo;

public class WorkLoadPayVoucherHandler extends AbstractFDCVoucherHandler  implements IFDCPayVoucherHandler {
	public WorkLoadPayVoucherHandler(Context ctx,IFDCVoucherEntryCreator creator) {
		setCtx(ctx);
		setFDCVoucherEntryCreator(creator);
	}

	public IObjectCollection createPayEntrys(Map dataMap) throws BOSException, EASBizException{
		init(dataMap);
		IObjectCollection coll = null;

		IObjectCollection paySplitInfos=(IObjectCollection)dataMap.get("paySplitInfos");
		for(Iterator iter=paySplitInfos.iterator();iter.hasNext();){
			IObjectCollection onecoll=createOnePaymentPayEntrys((IObjectValue)iter.next());
			if(coll==null)
				coll = onecoll;
			else
				coll.addObjectCollection(onecoll);
		}
		return coll;
	}

	protected IObjectCollection createOnePaymentPayEntrys(IObjectValue splitInfo) throws BOSException, EASBizException{
		IObjectCollection coll = null;
		IObjectCollection onecoll = createOnePaymentOtherPayEntrys(splitInfo);
		if(coll==null)
			coll = onecoll;
		else
			coll.addObjectCollection(onecoll);
		return coll;
		
	}
	
	/**
	 * 子类通过拆分生成其它分录，如应付账款，保修款
	 * @param splitInfo
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	protected IObjectCollection createOnePaymentOtherPayEntrys(IObjectValue splitInfo) throws EASBizException, BOSException{
		IObjectCollection coll = new WorkLoadPayVoucherEntryCollection();
		WorkLoadConfirmBillInfo workLoadConfirmBillInfo = null;

		workLoadConfirmBillInfo = ((PaymentSplitInfo)splitInfo).getWorkLoadConfirmBill();
		for(Iterator it=((PaymentSplitInfo)splitInfo).getEntrys().iterator();it.hasNext();){
			PaymentSplitEntryInfo entryInfo = (PaymentSplitEntryInfo)it.next();
			WorkLoadPayVoucherEntryInfo voucherEntryInfo = null;
			/***
			 * 银行科目（付款单付款科目）
			 */
			//coll.addObject(createPayEntryPayAccount(workLoadConfirmBillInfo));
			if(entryInfo.isIsLeaf()){
				if(workLoadConfirmBillInfo.isHasSettled())
				{
					/**
					 * 处理结算后的工程量拆分,要生成应付保修款,类型第一笔结算款
					 * 应付账款_开发成本_进度款=归属金额-归属付款金额 （付款拆分）
					 * 应付账款_开发成本_保修款：付款拆分上的保修款拆分金额
					 */
					voucherEntryInfo = createPayEntryProAccount(workLoadConfirmBillInfo, entryInfo,true);
					coll.addObject(voucherEntryInfo);
					voucherEntryInfo = createPayEntrySettAccount(workLoadConfirmBillInfo, entryInfo);
					coll.addObject(voucherEntryInfo);
					
				}else{
					/***
					 * 
					 * 进度款,保修款
					 * 应付账款_开发成本_进度款=归属金额-归属付款金额 （付款拆分）
					 * 周勇
					 */
					voucherEntryInfo = createPayEntryProAccount(workLoadConfirmBillInfo, entryInfo,false);
					coll.addObject(voucherEntryInfo);
				}

				
			}
		}
		return coll;
	}
	
	/**
	 * 应付账款_开发成本_进度款=归属成本金额-归属付款金额 （付款拆分）
	 * @param workLoadConfirmBillInfo
	 * @param entryInfo
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected WorkLoadPayVoucherEntryInfo createPayEntryProAccount(WorkLoadConfirmBillInfo workLoadConfirmBillInfo, PaymentSplitEntryInfo entryInfo,boolean hasSettled) throws BOSException, EASBizException {
		WorkLoadPayVoucherEntryInfo voucherEntryInfo;
		voucherEntryInfo = new WorkLoadPayVoucherEntryInfo();
		voucherEntryInfo.setId(BOSUuid.create(voucherEntryInfo.getBOSType()));
		
		//应付账款_开发成本_进度款=归属成本金额 - 归属付款金额 （付款拆分）
		//第一笔结算款 还要再 - 保修金金额
		
		BigDecimal amount = FDCHelper.toBigDecimal(entryInfo.getAmount())
							.subtract(FDCHelper.toBigDecimal(entryInfo.getPayedAmt()));
		if(hasSettled)
			amount = amount.subtract(FDCHelper.toBigDecimal(entryInfo.getQualityAmount()));
		voucherEntryInfo.setCurProject(entryInfo.getCostAccount().getCurProject());
		voucherEntryInfo.setAmount(amount);
		voucherEntryInfo.setLocateAmount(amount);
		voucherEntryInfo.setExchangeRate(FDCHelper.ONE);
		voucherEntryInfo.setSupplier(workLoadConfirmBillInfo.getContractBill().getPartB());
		voucherEntryInfo.setCurrency(getBaseCurrency());
		voucherEntryInfo.setParent(workLoadConfirmBillInfo);
		voucherEntryInfo.setType(FDCConstants.FDC_INIT_PROJECTPRICEINCONTRACT);
		voucherEntryInfo.setAccountView(beforeAccountViewInfo.getProAccount());
		voucherEntryInfo.setCostAccount(entryInfo.getCostAccount());
		return voucherEntryInfo;
	}
	
	/**
	 * 应付账款_开发成本_保修款 : 付款拆分上的保修款拆分金额
	 * @param workLoadConfirmBillInfo
	 * @param entryInfo
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected WorkLoadPayVoucherEntryInfo createPayEntryGrtAccount(WorkLoadConfirmBillInfo workLoadConfirmBillInfo, PaymentNoCostSplitEntryInfo entryInfo) throws BOSException, EASBizException {
		WorkLoadPayVoucherEntryInfo voucherEntryInfo;
		voucherEntryInfo = new WorkLoadPayVoucherEntryInfo();
		voucherEntryInfo.setId(BOSUuid.create(voucherEntryInfo.getBOSType()));
		//付款拆分上的保修款拆分金额
		voucherEntryInfo.setAmount(entryInfo.getSplitQualityAmt());
		voucherEntryInfo.setCurProject(entryInfo.getCurProject());
		voucherEntryInfo.setLocateAmount(entryInfo.getSplitQualityAmt());
		voucherEntryInfo.setExchangeRate(FDCHelper.ONE);
		voucherEntryInfo.setSupplier(workLoadConfirmBillInfo.getContractBill().getPartB());
		voucherEntryInfo.setCurrency(getBaseCurrency());
		voucherEntryInfo.setParent(workLoadConfirmBillInfo);
		voucherEntryInfo.setType(FDCConstants.FDC_INIT_PROJECTPRICEINCONTRACT);
		voucherEntryInfo.setAccountView(beforeAccountViewInfo.getSettAccount());
		return voucherEntryInfo;
	}


	/**
	 * 应付账款_开发成本_保修款 : 付款拆分上的保修款拆分金额
	 * @param workLoadConfirmBillInfo
	 * @param entryInfo
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected WorkLoadPayVoucherEntryInfo createPayEntrySettAccount(WorkLoadConfirmBillInfo workLoadConfirmBillInfo, PaymentSplitEntryInfo entryInfo) throws BOSException, EASBizException {
		WorkLoadPayVoucherEntryInfo voucherEntryInfo;
		voucherEntryInfo = new WorkLoadPayVoucherEntryInfo();
		voucherEntryInfo.setId(BOSUuid.create(voucherEntryInfo.getBOSType()));
		//付款拆分上的保修款拆分金额
		voucherEntryInfo.setAmount(entryInfo.getQualityAmount());
		voucherEntryInfo.setCurProject(entryInfo.getCostAccount().getCurProject());
		voucherEntryInfo.setLocateAmount(entryInfo.getQualityAmount());
		voucherEntryInfo.setExchangeRate(FDCHelper.ONE);
		voucherEntryInfo.setSupplier(workLoadConfirmBillInfo.getContractBill().getPartB());
		voucherEntryInfo.setCurrency(getBaseCurrency());
		voucherEntryInfo.setParent(workLoadConfirmBillInfo);
		voucherEntryInfo.setType(FDCConstants.FDC_INIT_PROJECTPRICEINCONTRACT);
		voucherEntryInfo.setAccountView(beforeAccountViewInfo.getSettAccount());
		return voucherEntryInfo;
	}
	
	
	private final static String PAYENTRY_INSERT_SQL = "INSERT INTO T_FNC_WorkLoadPayVoucherEntry" +
	"(FID,FSeq,FCurrencyid,FSupplierid,FAccountViewID,FDesc,FExchangeRate,FLocateAmount,FAmount,FType,FParentID,FCurProjectID,fcostaccountId) values " +
	"(?,?,?,?,?,?,?,?,?,?,?,?,?)";	
	public void save(Map param, IObjectCollection payEntrys) throws BOSException {
		WorkLoadConfirmBillInfo info = null;
		Map workLoadBills = (HashMap) param.get("paymentBills");
		FDCSQLBuilder builder = new FDCSQLBuilder(getCtx());
		builder.setPrepareStatementSql(PAYENTRY_INSERT_SQL);
		builder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);
		if (payEntrys != null) {
			for (Iterator it = payEntrys.iterator(); it.hasNext();) {
				WorkLoadPayVoucherEntryInfo entryInfo = (WorkLoadPayVoucherEntryInfo) it.next();
				String workloadId = entryInfo.getParent().getId().toString();
				/***************************************************************
				 * 如果是复杂模式，且已经同步过了 就不再同步了，直接跳过
				 */
				if (hasSynchBills.containsKey(workloadId)) {
					continue;
				}
				if (workLoadBills.containsKey(workloadId)) {
					info = (WorkLoadConfirmBillInfo) workLoadBills.get(workloadId);
					entryInfo.setSeq(info.getPayVoucherEntrys().size() + 1);
					info.getPayVoucherEntrys().add(entryInfo);
					builder.addParam(entryInfo.getId().toString());
					builder.addParam(new Integer(entryInfo.getSeq()));
					builder.addParam(entryInfo.getCurrency().getId().toString());
					builder.addParam(entryInfo.getSupplier().getId().toString());
					builder.addParam(entryInfo.getAccountView().getId().toString());
					builder.addParam(entryInfo.getDesc());
					builder.addParam(entryInfo.getExchangeRate());
					builder.addParam(FDCHelper.toBigDecimal(entryInfo.getLocateAmount()));
					builder.addParam(FDCHelper.toBigDecimal(entryInfo.getAmount()));
					builder.addParam(entryInfo.getType());
					builder.addParam(entryInfo.getParent().getId().toString());
					builder.addParam(entryInfo.getCurProject() == null ? null : entryInfo.getCurProject().getId().toString());
					builder.addParam(entryInfo.getCostAccount()==null?null:entryInfo.getCostAccount().getId().toString());
					builder.addBatch();
				}
			}
			builder.executeBatch();
		}

	}
}
