package com.kingdee.eas.fdc.invite.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.invite.SupplierQualifyFactory;
import com.kingdee.eas.fdc.invite.SupplierQualifyInfo;

public class SupplierQualifyControllerBean extends AbstractSupplierQualifyControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.SupplierQualifyControllerBean");

	@Override
	protected boolean _isHasStartBid(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		SupplierQualifyInfo info = SupplierQualifyFactory.getLocalInstance(ctx).getSupplierQualifyInfo(pk);
		return info.isHasStartBid();
	}

}