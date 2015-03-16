package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.dap.DAPTransImpl;
import com.kingdee.eas.fdc.aimcost.VoucherForDynamicFactory;
import com.kingdee.eas.fdc.aimcost.VoucherForDynamicInfo;
import com.kingdee.eas.fdc.aimcost.client.VoucherForDynamicEditUI;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountInfo;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.fdc.basedata.ICostAccountWithAccount;
import com.kingdee.eas.fdc.basedata.PaymentTypeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.VoucherForProjectFactory;
import com.kingdee.eas.fdc.basedata.VoucherForProjectInfo;
import com.kingdee.eas.fdc.basedata.client.VoucherForProjectEditUI;
import com.kingdee.eas.fdc.finance.client.PaymentSplitEditUI;
import com.kingdee.eas.fdc.finance.client.ProductSettleBillEditUI;
import com.kingdee.eas.fdc.finance.client.SettForPayVoucherEditUI;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.client.IDAPTrans;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

public class PaymentSplitVoucherHelper {
//	public static void voucherForProductSett(IObjectPK pk) throws Exception{
//		ProductSettleBillEditUI ui = new ProductSettleBillEditUI();
//		ProductSettleBillInfo info = ProductSettleBillFactory.getRemoteInstance().getProductSettleBillInfo(pk);
//		IDAPTrans dapTrans = new DAPTransImpl(ui);
//		CoreBillBaseCollection sourceBillCollection = new CoreBillBaseCollection();		
//		sourceBillCollection.add(info);
//		dapTrans.trans(sourceBillCollection);
//	}
	
	public static void SettForPayVoucher(IObjectPK pk) throws Exception{
		SettForPayVoucherEditUI ui = new SettForPayVoucherEditUI(); 
		SettForPayVoucherInfo vouPro = SettForPayVoucherFactory.getRemoteInstance().getSettForPayVoucherInfo(pk);
		IDAPTrans dapTrans = new DAPTransImpl(ui);
		CoreBillBaseCollection sourceBillCollection = new CoreBillBaseCollection();		
		sourceBillCollection.add(vouPro);
		dapTrans.trans(sourceBillCollection);
	}
	
	public static void voucherForDynamic(IObjectPK pk) throws Exception{
		VoucherForDynamicEditUI ui = new VoucherForDynamicEditUI(); 
		VoucherForDynamicInfo vouPro = VoucherForDynamicFactory.getRemoteInstance().getVoucherForDynamicInfo(pk);
		IDAPTrans dapTrans = new DAPTransImpl(ui);
		CoreBillBaseCollection sourceBillCollection = new CoreBillBaseCollection();		
		sourceBillCollection.add(vouPro);
		dapTrans.trans(sourceBillCollection);
	}
	
	public static void voucherForProject(IObjectPK pk) throws Exception{
		VoucherForProjectEditUI ui = new VoucherForProjectEditUI(); 
		VoucherForProjectInfo vouPro = VoucherForProjectFactory.getRemoteInstance().getVoucherForProjectInfo(pk);
		IDAPTrans dapTrans = new DAPTransImpl(ui);
		CoreBillBaseCollection sourceBillCollection = new CoreBillBaseCollection();		
		sourceBillCollection.add(vouPro);
		dapTrans.trans(sourceBillCollection);
	}
	
	public static void voucherForPaySplit(String id) throws Exception{
		PaymentSplitEditUI ui = new PaymentSplitEditUI(); 
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("entrys.*");
		sic.add("entrys.costAccount.*");
		sic.add("entrys.product.*");
		sic.add("entrys.apportionType.*");
		sic.add("entrys.accountView.*");
//		sic.add("hisStatus.*");
//		sic.add("hisVoucher.*");
//		sic.add("beAccount.*");
//		sic.add("beAccount.proAccount.*");
		sic.add("paymentBill.id");
		sic.add("paymentBill.contractBillId");
		sic.add("paymentBill.fdcPayType.*");
		sic.add("paymentBill.fiVouchered");
//		sic.add("paymentBill.accountant");
		sic.add("paymentBill.voucher.*");
//		sic.add("paymentBill.curProject.projectStatus.*");
        PaymentSplitInfo info = (PaymentSplitInfo)getBizInterface().getValue(new ObjectUuidPK(id),sic);
        if(info.getPaymentBill()!=null&&info.getPaymentBill().getFdcPayType().getId()!=null){
			PaymentTypeInfo paymentType = PaymentTypeFactory.getRemoteInstance().
			getPaymentTypeInfo(new ObjectUuidPK(info.getPaymentBill().getFdcPayType().getId()));
			if(paymentType.getPayType()!=null&&paymentType.getPayType().getId().equals(BOSUuid.read("Ga7RLQETEADgAADDwKgOlOwp3Sw="))){
				return;
			}
        }
        if(info.isFivouchered()){
        	FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent.id",id));
			filter.getFilterItems().add(new FilterItemInfo("isLeaf",Boolean.TRUE));
			filter.getFilterItems().add(new FilterItemInfo("accountView",null));
			if(PaymentSplitEntryFactory.getRemoteInstance().exists(filter)){
				getAccFromCost(id);
			}       	
			IDAPTrans dapTrans = new DAPTransImpl(ui);
			CoreBillBaseCollection sourceBillCollection = new CoreBillBaseCollection();
			info = (PaymentSplitInfo)getBizInterface().getValue(new ObjectUuidPK(id));
			info.setFivouchered(false);
			sourceBillCollection.add(info);
			dapTrans.trans(sourceBillCollection);
		}
	}
	
	protected static com.kingdee.eas.framework.ICoreBase getBizInterface()
	throws Exception {
		return PaymentSplitFactory.getRemoteInstance();
	}
	
	protected static void getAccFromCost(String id) throws Exception{
    	ICostAccountWithAccount iCostAccountWithAccount = CostAccountWithAccountFactory.getRemoteInstance();
    	IPaymentSplitEntry iPaymentSplitEntry = PaymentSplitEntryFactory.getRemoteInstance();
    	PaymentSplitEntryCollection coll;
    	CostAccountWithAccountCollection entryColl = null;
    	CostAccountWithAccountInfo entryInfo = new CostAccountWithAccountInfo();
    	ICostAccount iCostAccount = CostAccountFactory.getRemoteInstance();
    	EntityViewInfo view=new EntityViewInfo();			
    	FilterInfo filter=new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("parent.id",id));
    	filter.getFilterItems().add(new FilterItemInfo("isLeaf",Boolean.TRUE));
    	view.setFilter(filter);
    	view.getSelector().add(new SelectorItemInfo("id"));
    	view.getSelector().add(new SelectorItemInfo("seq"));
    	view.getSelector().add(new SelectorItemInfo("costAccount.*"));
    	view.getSelector().add(new SelectorItemInfo("accountView.*"));
    	coll = iPaymentSplitEntry.getPaymentSplitEntryCollection(view);
    	int entrySize = coll.size();
    	for(int j=0;j<entrySize;j++){			
    		PaymentSplitEntryInfo info = coll.get(j);
    		CostAccountInfo costAcc = info.getCostAccount();
    		getAccForEntry(info,costAcc,iCostAccountWithAccount,entryColl,entryInfo,iPaymentSplitEntry,iCostAccount);				
    	}
    }
    
	private static void getAccForEntry(PaymentSplitEntryInfo info, CostAccountInfo costAcc,
			ICostAccountWithAccount iCostAccountWithAccount, CostAccountWithAccountCollection entryColl,
			CostAccountWithAccountInfo entryInfo, IPaymentSplitEntry iPaymentSplitEntry
			,ICostAccount iCostAccount) throws Exception{			
		EntityViewInfo entryView=new EntityViewInfo();
		FilterInfo entryFilter=new FilterInfo();
		entryFilter.getFilterItems().add(new FilterItemInfo("costAccount.id",costAcc.getId()));		
		if(!iCostAccountWithAccount.exists(entryFilter)){
			if(costAcc.getLevel()==1){
				MsgBox.showWarning("成本科目未设置与会计科目的对应关系！");
				SysUtil.abort();
			}
			else{
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("id");	
				selector.add("level");	
				selector.add("parent.*");
				selector.add("curProject.name");
				selector.add("fullOrgUnit.name");
//				if(costAcc.getParent().getId()!= null)
				CostAccountInfo parent = iCostAccount.getCostAccountInfo(new ObjectUuidPK(costAcc.getParent().getId()), selector);				
				getAccForEntry(info,parent,iCostAccountWithAccount,entryColl,entryInfo,iPaymentSplitEntry,iCostAccount);
			}
		}
		entryView.setFilter(entryFilter);
		entryColl = iCostAccountWithAccount.getCostAccountWithAccountCollection(entryView);
		if(entryColl.size()==1){
			entryInfo = entryColl.get(0);
			if(entryInfo.getAccount()!=null){
				info.setAccountView(entryInfo.getAccount());
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("accountView");						
				iPaymentSplitEntry.updatePartial(info, selector);
			}
		}		
	}
	
}
