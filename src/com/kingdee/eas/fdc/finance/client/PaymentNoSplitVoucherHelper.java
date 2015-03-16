package com.kingdee.eas.fdc.finance.client;

import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.dap.DAPTransImpl;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountInfo;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.fdc.basedata.ICostAccountWithAccount;
import com.kingdee.eas.fdc.basedata.PaymentTypeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.finance.IPaymentNoCostSplitEntry;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryCollection;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.client.IDAPTrans;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

public class PaymentNoSplitVoucherHelper {
	public static void voucherForPaySplit(String id) throws Exception{
		PaymentNoCostSplitEditUI ui = new PaymentNoCostSplitEditUI(); 
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
        PaymentNoCostSplitInfo info = (PaymentNoCostSplitInfo)getBizInterface().getValue(new ObjectUuidPK(id),sic);
        if(info.getPaymentBill()!=null&&info.getPaymentBill().getFdcPayType().getId()!=null){
			PaymentTypeInfo paymentType = PaymentTypeFactory.getRemoteInstance().
			getPaymentTypeInfo(new ObjectUuidPK(info.getPaymentBill().getFdcPayType().getId()));
			if(paymentType.getPayType()!=null&&paymentType.getPayType().getId().equals(BOSUuid.read("Ga7RLQETEADgAADDwKgOlOwp3Sw="))){
				return;
			}
        }
        if(info.isFivouchered()){
//        	FilterInfo filter=new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("parent.id",id));
//			filter.getFilterItems().add(new FilterItemInfo("isLeaf",Boolean.TRUE));
//			filter.getFilterItems().add(new FilterItemInfo("accountView",null));
//			if(PaymentNoCostSplitEntryFactory.getRemoteInstance().exists(filter)){
//				getAccFromCost(id);
//			}       	
			IDAPTrans dapTrans = new DAPTransImpl(ui);
			CoreBillBaseCollection sourceBillCollection = new CoreBillBaseCollection();
			info = (PaymentNoCostSplitInfo)getBizInterface().getValue(new ObjectUuidPK(id));
			info.setFivouchered(false);
			sourceBillCollection.add(info);
			dapTrans.trans(sourceBillCollection);
		}
	}
	
	protected static com.kingdee.eas.framework.ICoreBase getBizInterface()
	throws Exception {
		return PaymentNoCostSplitFactory.getRemoteInstance();
	}
		
}
