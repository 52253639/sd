package com.kingdee.eas.fdc.tenancy.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.tenancy.AttachRentAdjustEntrysCollection;
import com.kingdee.eas.fdc.tenancy.AttachRentAdjustEntrysInfo;
import com.kingdee.eas.fdc.tenancy.AttachRentAdjustInfo;
import com.kingdee.eas.fdc.tenancy.AttachResourceFactory;
import com.kingdee.eas.fdc.tenancy.AttachResourceInfo;

public class AttachRentAdjustControllerBean extends AbstractAttachRentAdjustControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.AttachRentAdjustControllerBean");
    protected boolean _execute(Context ctx, String id) throws BOSException, EASBizException {
    	SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("sellProject.*"));
		sic.add(new SelectorItemInfo("entrys.*"));
		sic.add(new SelectorItemInfo("entrys.subarea.*"));
		sic.add(new SelectorItemInfo("entrys.building.*"));
		AttachRentAdjustInfo attachInfo = (AttachRentAdjustInfo) this.getValue(ctx,
				new ObjectUuidPK(BOSUuid.read(id)), sic);
		AttachRentAdjustEntrysCollection attachColl = attachInfo.getEntrys();
		for(int i=0;i<attachColl.size();i++)
		{
			AttachRentAdjustEntrysInfo attachEntrysInfo = attachColl.get(i);
			AttachResourceInfo info = attachEntrysInfo.getAttach();
			info.setRentType(attachEntrysInfo.getNewRentType());
			info.setStandardRent(attachEntrysInfo.getNewStandardRent());
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