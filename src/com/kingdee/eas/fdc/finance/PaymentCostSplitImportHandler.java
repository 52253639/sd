package com.kingdee.eas.fdc.finance;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;

public class PaymentCostSplitImportHandler implements ISplitImportHandler{
	private Context ctx=null;
	public PaymentCostSplitImportHandler(Context ctx) {
		this.ctx=ctx;
	}
	public void handle(IObjectValue splitInfo) throws BOSException {
		PaymentSplitInfo info=(PaymentSplitInfo)splitInfo;
		if(info.getContractBill().isHasSettled()){
			handleSettle(info);
		}else{
			handleNoSettle(info);
		}
		handleSplitedAmt(info);
	}
	private void handleNoSettle(PaymentSplitInfo info)throws BOSException{
		//set costamt,splitedamt,
		for(Iterator iter=info.getEntrys().iterator();iter.hasNext();){
			PaymentSplitEntryInfo entry=(PaymentSplitEntryInfo)iter.next();
			entry.setCostAmt(FDCHelper.add(entry.getContractAmt(),entry.getChangeAmt()));
		}
	}
	
	private void handleSettle(PaymentSplitInfo info) throws BOSException {
		for (Iterator iter = info.getEntrys().iterator(); iter.hasNext();) {
			PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) iter.next();
			entry.setCostAmt(entry.getAmount());
			entry.setAmount(FDCHelper.ZERO);
			// 应付进度款=成本拆分金额-保修款拆分金额
			entry.setShouldPayAmt(FDCHelper.subtract(entry.getCostAmt(), entry.getBigDecimal("grtSplitAmt")));
			if (info.isIsProgress() 
					|| (info.getWorkLoadConfirmBill() != null && info.getWorkLoadConfirmBill().isHasSettled())) {// 结算后的工程量确认
				entry.setQualityAmount(entry.getBigDecimal("grtSplitAmt"));
			}
		}
	}
	
	/**
	 * 处理已拆分金额
	 * @param info
	 */
	private void handleSplitedAmt(PaymentSplitInfo info) throws BOSException{
		String contractId=info.getContractBill().getId().toString();
		//以前的付款拆分分录
		Map paySplitEntryMap=new HashMap();
		//已拆分的保修款付款拆分分录

		//所有之前的付款拆分
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("contractBill.id", contractId);
		view.getFilter().getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
		//TODO 工程量已拆分成本包括付款拆分的?
		//view.getFilter().getFilterItems().add(new FilterItemInfo("isWorkLoadBill",Boolean.valueOf(info.isIsWorkLoadBill())));
		if(info!=null&&info.getId()!=null){
			view.getFilter().getFilterItems().add(new FilterItemInfo("id", info.getId().toString(), CompareType.NOTEQUALS));
		}
		view.getSelector().add("entrys.amount");
		view.getSelector().add("entrys.payedAmt");
		view.getSelector().add("entrys.product.id");
		view.getSelector().add("entrys.costAccount.id");
		view.getSelector().add("entrys.costBillId");
		view.getSelector().add("entrys.splitType");
		view.getSelector().add("entrys.parent.paymentBill.fdcPayType.payType.id");
		
		PaymentSplitCollection paymentSplitCollection = PaymentSplitFactory.getLocalInstance(ctx).getPaymentSplitCollection(view);
		for(int i=0;i<paymentSplitCollection.size();i++){
			for (Iterator iter = paymentSplitCollection.get(i).getEntrys().iterator(); iter.hasNext();) {
				PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) iter.next();
				if(entry.getCostAccount()==null){
					continue;
				}
				String key=entry.getCostAccount().getId().toString();
				String costbillid = entry.getCostBillId()==null?"":entry.getCostBillId().toString();
				key=key+costbillid;
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
				System.out.println("54: "+mapEntry.getAmount());
				mapEntry.setPayedAmt(FDCHelper.add(mapEntry.getPayedAmt(), entry.getPayedAmt()));
				if(entry.getParent().getPaymentBill()!=null&&entry.getParent().getPaymentBill().getFdcPayType()!=null
						&&entry.getParent().getPaymentBill().getFdcPayType().getPayType()!=null
						&&entry.getParent().getPaymentBill().getFdcPayType().getPayType().getId().equals(PaymentTypeInfo.keepID)){
					mapEntry.setSplitQualityAmt(FDCHelper.add(mapEntry.getSplitQualityAmt(), entry.getPayedAmt()));
				}
					
			}
		}
		
		for(int i=0;i<info.getEntrys().size();i++){
			PaymentSplitEntryInfo entry = info.getEntrys().get(i);
			String key=entry.getCostAccount().getId().toString();
			String costbillid = entry.getCostBillId()==null?"":entry.getCostBillId().toString();
			key=key+costbillid;
			if(entry.getSplitType()!=null){
				key=key+entry.getSplitType().getValue();
			}
			if(entry.getProduct()!=null){
				key=key+entry.getProduct().getId().toString();
			}
//			SettlementCostSplitEntryInfo settleEntry=(SettlementCostSplitEntryInfo)settleSplitEntryMap.get(key);
			PaymentSplitEntryInfo paySplitEntry=(PaymentSplitEntryInfo)paySplitEntryMap.get(key);
//			PaymentSplitEntryInfo paySplitGrtEntry=(PaymentSplitEntryInfo)paySplitGrtEntryMap.get(key);
			//成本拆分金额
//			entry.setCostAmt(settleEntry!=null?settleEntry.getAmount():FDCHelper.ZERO);
			//保修款拆分金额
//			entry.setQualityAmount(settleEntry.getGrtSplitAmt());
			//应付进度款=成本拆分金额-保修款拆分金额
//			entry.setShouldPayAmt(settleEntry!=null?FDCNumberHelper.subtract(settleEntry.getAmount(), settleEntry.getGrtSplitAmt()):FDCHelper.ZERO);
			
			//已拆分成本金额
			entry.setSplitedCostAmt(paySplitEntry!=null?paySplitEntry.getAmount():FDCHelper.ZERO);
			//已拆分付款金额实际为已拆分进度款金额不包括保修款部分所以要减去保修款的拆分金额
			entry.setSplitedPayedAmt(paySplitEntry!=null?FDCHelper.subtract(paySplitEntry.getPayedAmt(), paySplitEntry.getSplitQualityAmt()):FDCHelper.ZERO);			//已拆分保修款还是用以前的逻辑,暂时不处理
//			entry.setSplitQualityAmt(paySplitGrtEntry.getSplitQualityAmt());
/*				if(info.isIsProgress()){
				entry.setQualityAmount(entry.getBigDecimal("grtSplitAmt"));
			}*/
		}
	
	}
}
