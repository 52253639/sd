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
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;

import com.kingdee.eas.fdc.tenancy.AttachResourceFactory;
import com.kingdee.eas.fdc.tenancy.AttachResourceInfo;
import com.kingdee.eas.fdc.tenancy.AttachResourceRentBillInfo;
import com.kingdee.eas.fdc.tenancy.AttachResourceRentEntrysCollection;
import com.kingdee.eas.fdc.tenancy.AttachResourceRentEntrysInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public class AttachResourceRentBillControllerBean extends AbstractAttachResourceRentBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.AttachResourceRentBillControllerBean");
    protected boolean _execute(Context ctx, String id) throws BOSException, EASBizException {
    	SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("sellProject.*"));
		sic.add(new SelectorItemInfo("entrys.*"));
		sic.add(new SelectorItemInfo("entrys.subarea.*"));
		sic.add(new SelectorItemInfo("entrys.building.*"));
		AttachResourceRentBillInfo attachInfo = (AttachResourceRentBillInfo) this.getValue(ctx,
				new ObjectUuidPK(BOSUuid.read(id)), sic);
		AttachResourceRentEntrysCollection attachColl = attachInfo.getEntrys();
		for(int i=0;i<attachColl.size();i++)
		{
			AttachResourceRentEntrysInfo attachEntrysInfo = attachColl.get(i);
			AttachResourceInfo info = attachEntrysInfo.getAttach();
			info.setRentType(attachEntrysInfo.getRentType());
			info.setStandardRent(attachEntrysInfo.getStandardRent());
			sic = new SelectorItemCollection();
			sic.add("rentType");
			sic.add("standardRent");
			AttachResourceFactory.getLocalInstance(ctx).updatePartial(info, sic);
		}
		attachInfo.setIsExecuted(true);
		sic = new SelectorItemCollection();
		sic.add("isExecuted");
		this.updatePartial(ctx, attachInfo, sic);
    	return true;
    }
}