package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutText;
import com.kingdee.eas.fdc.contract.IConChangeSplit;
import com.kingdee.eas.fdc.contract.IConChangeSplitEntry;
import com.kingdee.eas.fdc.contract.IContractCostSplit;
import com.kingdee.eas.fdc.contract.IContractCostSplitEntry;
import com.kingdee.eas.fdc.contract.ISettlementCostSplit;
import com.kingdee.eas.fdc.contract.ISettlementCostSplitEntry;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitInfo;
import com.kingdee.eas.fdc.finance.IPaymentSplit;
import com.kingdee.eas.fdc.finance.IPaymentSplitEntry;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;

public class FDCCostSplitAdapter
{
	private FDCCostSplit fDCostSplit;
	private Context ctx;
	private static BOSObjectType conSplitType=(new ContractCostSplitInfo()).getBOSType();
	private static BOSObjectType conChangeSplitType=(new ConChangeSplitInfo()).getBOSType();
	private static BOSObjectType settlementSplitType=(new SettlementCostSplitInfo()).getBOSType();
	private static BOSObjectType paymentSplitType=(new PaymentSplitInfo()).getBOSType();
	/**
	 * @param ctx  客户端为null 服务器端为Context
	 */
	public FDCCostSplitAdapter(Context ctx){
		fDCostSplit=new FDCCostSplit(ctx);
		this.ctx=ctx;
	}
	/**
	 * 通过id的BOSObjectType确定具体的刷新类型,调用刷新
	 * @author sxhong  		Date 2007-5-29
	 */
	public void refresh(String id)throws Exception{
		final BOSObjectType type = BOSUuid.read(id).getType();
		if(type.equals(conSplitType)){
			refreshContract(id);
		}
		if(type.equals(conChangeSplitType)){
			refreshConChange(id);
		}
		if(type.equals(settlementSplitType)){
			refreshSettlement(id);
		}
		if(type.equals(paymentSplitType)){
			refreshPayment(id);
		}
	}
	/**
	 * 
	 * @author sxhong  		Date 2006-12-18
	 * @param id	要刷新的拆分单据的ID
	 * @param ctx	
	 * @throws Exception
	 */
	public void refreshContract(String id) throws Exception{
		if(id==null){
			return;
		}
		IContractCostSplit iContractCostSplit = null;
		IContractCostSplitEntry iContractCostSplitEntry = null;
		SelectorItemCollection selector=getSelectors();
		selector.add("contractBill.id");
		selector.add("contractBill.CU.id");
		if(ctx!=null){
			iContractCostSplit=ContractCostSplitFactory.getLocalInstance(ctx);
			iContractCostSplitEntry=ContractCostSplitEntryFactory.getLocalInstance(ctx);
		}else{
			iContractCostSplit=ContractCostSplitFactory.getRemoteInstance();
			iContractCostSplitEntry=ContractCostSplitEntryFactory.getRemoteInstance();
		}
		ContractCostSplitInfo splitbill=iContractCostSplit.getContractCostSplitInfo(new ObjectUuidPK(BOSUuid.read(id)),selector);
		fDCostSplit.refreshApportionAmount(splitbill, iContractCostSplitEntry);
//		splitbill=iContractCostSplit.getContractCostSplitInfo(new ObjectUuidPK(BOSUuid.read(id)),selector);
		fDCostSplit.collectCostSplit(CostSplitBillTypeEnum.CONTRACTSPLIT, splitbill.getContractBill(), BOSUuid.read(id), splitbill.getEntrys());
	
	}
	
	public void refreshConChange(String id) throws Exception{
		if(id==null){
			return;
		}
		
		IConChangeSplit iConChangeSplit = null;
		IConChangeSplitEntry iConChangeSplitEntry = null;
		SelectorItemCollection selector=getSelectors();
		selector.add("contractChange.id");
		selector.add("contractChange.CU.id");
		if(ctx!=null){
			iConChangeSplit=ConChangeSplitFactory.getLocalInstance(ctx);
			iConChangeSplitEntry=ConChangeSplitEntryFactory.getLocalInstance(ctx);
		}else{
			iConChangeSplit=ConChangeSplitFactory.getRemoteInstance();
			iConChangeSplitEntry=ConChangeSplitEntryFactory.getRemoteInstance();
		}
		ConChangeSplitInfo splitbill=iConChangeSplit.getConChangeSplitInfo(new ObjectUuidPK(BOSUuid.read(id)),selector);
		fDCostSplit.refreshApportionAmount(splitbill, iConChangeSplitEntry);
		
		fDCostSplit.collectCostSplit(CostSplitBillTypeEnum.CNTRCHANGESPLIT, splitbill.getContractChange(), BOSUuid.read(id), splitbill.getEntrys());
	}
	
	public void refreshPayment(String id) throws Exception{
		if(id==null){
			return;
		}
		
		IPaymentSplit iPaymentSplit = null;
		IPaymentSplitEntry iPaymentSplitEntry = null;
		SelectorItemCollection selector=getSelectors();
		selector.add("paymentBill.id");
		selector.add("paymentBill.CU.id");
		selector.add("paymentBill.contractBillId");
//		selector.add("parent.*");
		if(ctx!=null){
			iPaymentSplit=PaymentSplitFactory.getLocalInstance(ctx);
			iPaymentSplitEntry=PaymentSplitEntryFactory.getLocalInstance(ctx);
		}else{
			iPaymentSplit=PaymentSplitFactory.getRemoteInstance();
			iPaymentSplitEntry=PaymentSplitEntryFactory.getRemoteInstance();
		}
		PaymentSplitInfo splitbill=iPaymentSplit.getPaymentSplitInfo(new ObjectUuidPK(BOSUuid.read(id)),selector);
		if(BOSUuid.read(splitbill.getPaymentBill().getContractBillId()).getType().equals((new ContractWithoutText().getType()))){
			fDCostSplit.refreshConWithoutTextPaymentSplit(splitbill, iPaymentSplitEntry);
			fDCostSplit.collectCostSplit(CostSplitBillTypeEnum.NOTEXTCONSPLIT, splitbill.getPaymentBill(), BOSUuid.read(id), splitbill.getEntrys());
			fDCostSplit.collectCostSplit(CostSplitBillTypeEnum.PAYMENTSPLIT, splitbill.getPaymentBill(), BOSUuid.read(id), splitbill.getEntrys());
		}else{//注释掉,经过讨论,暂不实现合同的刷新
//			fDCostSplit.refreshPaymentSplit(splitbill, iPaymentSplitEntry);
//			fDCostSplit.collectCostSplit(CostSplitBillTypeEnum.PAYMENTSPLIT, splitbill.getPaymentBill().getId(), splitbill.getEntrys());
		}
	}
	
	public void refreshSettlement(String id) throws Exception{
		if(id==null){
			return;
		}
		
		ISettlementCostSplit iSettlementCostSplit = null;
		ISettlementCostSplitEntry iSettlementCostSplitEntry = null;
		SelectorItemCollection selector=getSelectors();
		selector.add("settlementBill.id");
		selector.add("settlementBill.CU.id");
		selector.add("settlementBill.settlePrice");
		selector.add("settlementBill.qualityGuarante");
		if(ctx!=null){
			iSettlementCostSplit=SettlementCostSplitFactory.getLocalInstance(ctx);
			iSettlementCostSplitEntry=SettlementCostSplitEntryFactory.getLocalInstance(ctx);
		}else{
			iSettlementCostSplit=SettlementCostSplitFactory.getRemoteInstance();
			iSettlementCostSplitEntry=SettlementCostSplitEntryFactory.getRemoteInstance();
		}
		SettlementCostSplitInfo splitbill=iSettlementCostSplit.getSettlementCostSplitInfo(new ObjectUuidPK(BOSUuid.read(id)),selector);
		fDCostSplit.refreshSettlementSplitApportionAmount(splitbill, iSettlementCostSplitEntry);
		
		fDCostSplit.collectCostSplit(CostSplitBillTypeEnum.SETTLEMENTSPLIT, splitbill.getSettlementBill(), BOSUuid.read(id), splitbill.getEntrys());
	}
	
	private SelectorItemCollection getSelectors() {
		SelectorItemCollection sic=new SelectorItemCollection();
/*
		String prefix="entrys.";
        sic.add(new SelectorItemInfo(prefix + "*"));

        sic.add(new SelectorItemInfo(prefix + "apportionType.id"));
        sic.add(new SelectorItemInfo(prefix + "apportionType.name"));
        sic.add(new SelectorItemInfo(prefix + "product.*"));

        sic.add(new SelectorItemInfo(prefix + "costAccount"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.id"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.name"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.longNumber"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.displayName"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.isLeaf"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.level"));
        
        sic.add(new SelectorItemInfo(prefix + "costAccount.curProject"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.curProject.id"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.curProject.name"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.curProject.displayName"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.curProject.longNumber"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.curProject.isLeaf"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.curProject.level"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.curProject.parent.id"));*/
                
        
        return fDCostSplit.setSelectorsEntry(sic, false);
	}
	
	/**
	 * 
	 * @author sxhong  		Date 2006-12-14
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
