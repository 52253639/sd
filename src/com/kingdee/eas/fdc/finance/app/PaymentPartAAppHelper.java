package com.kingdee.eas.fdc.finance.app;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.finance.PartAMainContractFactory;
import com.kingdee.eas.fdc.finance.PartAMainContractInfo;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.framework.CoreBaseCollection;

public class PaymentPartAAppHelper {
	private PaymentPartAAppHelper(){}
	
	public static void save(Context ctx,PaymentBillInfo info) throws BOSException, EASBizException{
		CoreBaseCollection collection = (CoreBaseCollection)info.get("PartAMainContractEntrys");
		//delete
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("paymentBill.id", info.getId().toString()));
		PartAMainContractFactory.getLocalInstance(ctx).delete(filter);
		//save
		if(collection != null && collection.size()>0){
			for(int i=0;i<collection.size();i++){
				PartAMainContractInfo partAinfo=(PartAMainContractInfo)collection.get(i);
				PartAMainContractFactory.getLocalInstance(ctx).addnew(partAinfo);
			}
		}
	}
	
}
