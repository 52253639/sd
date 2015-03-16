package com.kingdee.eas.fdc.tenancy.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.tenancy.TenBillBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.framework.Result;
import com.kingdee.eas.fdc.tenancy.CustomerChangeEntryCollection;
import com.kingdee.eas.fdc.tenancy.CustomerChangeEntryInfo;
import com.kingdee.eas.fdc.tenancy.CustomerChangeNameCollection;
import com.kingdee.eas.fdc.tenancy.CustomerChangeNameFactory;
import com.kingdee.eas.fdc.tenancy.CustomerChangeNameInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;

public class CustomerChangeNameControllerBean extends AbstractCustomerChangeNameControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.CustomerChangeNameControllerBean");

    
    protected IObjectPK _save(Context ctx, IObjectValue model)
    		throws BOSException, EASBizException {
    	return super._save(ctx, model);
    }
    protected Result _save(Context ctx, IObjectCollection colls)
    		throws BOSException, EASBizException {
    	return super._save(ctx, colls);
    }
    protected IObjectPK _submit(Context ctx, IObjectValue model)
    		throws BOSException, EASBizException {
    	return super._submit(ctx, model);
    }
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
    	
    	//需要反写新客户到合同
    	SelectorItemCollection coll = new SelectorItemCollection();
    	coll.add("tenancyBill");
    	coll.add("newCustomer.propertyPercent");
		coll.add("newCustomer.description");
		coll.add("newCustomer.fdcCustomer.*");
		FilterInfo filter = new FilterInfo();
		CustomerChangeNameInfo info = CustomerChangeNameFactory.getLocalInstance(ctx).getCustomerChangeNameInfo(new ObjectUuidPK(billId),coll);
		TenancyBillInfo tenancyBill = info.getTenancyBill();
		filter.getFilterItems().add(new FilterItemInfo("tenancyBill",tenancyBill.getId()));
		//先删去合同上的原客户
		TenancyCustomerEntryFactory.getLocalInstance(ctx).delete(filter);
		//添加新客户
		TenancyCustomerEntryCollection tenancyCustoemrColl = customerChangeNameToTenancyCustomer(info.getNewCustomer(),tenancyBill);
		StringBuffer tenCustomerDes = new StringBuffer();
		for(int i = 0; i<tenancyCustoemrColl.size(); i++){
			TenancyCustomerEntryInfo customerinfo = tenancyCustoemrColl.get(i);
			TenancyCustomerEntryFactory.getLocalInstance(ctx).addnew(customerinfo);
			if (i != 0) {
				tenCustomerDes.append(",");
			}
			tenCustomerDes.append(customerinfo.getFdcCustomer().getName());
		}
		//同时更新租赁ListUI上的房号
		tenancyBill.setTenCustomerDes(tenCustomerDes.toString());
		SelectorItemCollection select = new SelectorItemCollection();
		select.add("tenCustomerDes");
		TenancyBillFactory.getLocalInstance(ctx).updatePartial(tenancyBill, select);
		super._audit(ctx, billId);
    }
	private TenancyCustomerEntryCollection customerChangeNameToTenancyCustomer(CustomerChangeEntryCollection customerChangeEntryCollection,TenancyBillInfo tenancyBill) {
		TenancyCustomerEntryCollection tenancyCustoemrColl = new TenancyCustomerEntryCollection();
		for(int i= 0 ; i<customerChangeEntryCollection.size();i++){
			TenancyCustomerEntryInfo tenCustomer = new  TenancyCustomerEntryInfo();
			CustomerChangeEntryInfo customerInfo = customerChangeEntryCollection.get(i);
			tenCustomer.setDescription(customerInfo.getDescription());
			tenCustomer.setPropertyPercent(customerInfo.getPropertyPercent());
			tenCustomer.setFdcCustomer(customerInfo.getFdcCustomer());
			tenCustomer.setTenancyBill(tenancyBill);
			tenancyCustoemrColl.add(tenCustomer);
		}
		return tenancyCustoemrColl;
	}
    
}