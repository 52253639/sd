package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
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
import com.kingdee.eas.framework.Result;
import com.kingdee.eas.framework.LineResult;
import com.kingdee.eas.framework.exception.EASMultiException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;

import java.lang.String;
import com.kingdee.eas.fdc.invite.InviteProjectException;



public abstract class AbstractInviteGetUserPortalFacadeControllerBean extends AbstractBizControllerBean implements InviteGetUserPortalFacadeController
{
    protected AbstractInviteGetUserPortalFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("78B5C29A");
    }

    public String getUserPortal(Context ctx, String clientIP) throws BOSException, InviteProjectException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("0416534f-5701-4e4f-85d2-2ca1123f9dce"), new Object[]{ctx, clientIP});
            invokeServiceBefore(svcCtx);
            String retValue = (String)_getUserPortal(ctx, clientIP);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } catch (InviteProjectException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract String _getUserPortal(Context ctx, String clientIP) throws BOSException, InviteProjectException;

}