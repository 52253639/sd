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
import com.kingdee.eas.common.EASBizException;



public abstract class AbstractInviteProjectAppraiseFacadeControllerBean extends AbstractBizControllerBean implements InviteProjectAppraiseFacadeController
{
    protected AbstractInviteProjectAppraiseFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("9B0FA931");
    }

    public void checkCanAddExpertAppraise(Context ctx, String inviteProjectID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("53c45c0e-eb29-4c01-8c34-58da8372a1b0"), new Object[]{ctx, inviteProjectID});
            invokeServiceBefore(svcCtx);
            _checkCanAddExpertAppraise(ctx, inviteProjectID);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _checkCanAddExpertAppraise(Context ctx, String inviteProjectID) throws BOSException, EASBizException;

    public void aotuSetInviteStatusAppraise(Context ctx) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("30cac54e-9bf5-465b-bc34-e3fe9ffb25df"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            _aotuSetInviteStatusAppraise(ctx);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _aotuSetInviteStatusAppraise(Context ctx) throws BOSException, EASBizException;

}