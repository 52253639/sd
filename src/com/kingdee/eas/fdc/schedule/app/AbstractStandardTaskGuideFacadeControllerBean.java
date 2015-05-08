package com.kingdee.eas.fdc.schedule.app;

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

import java.util.List;
import java.util.Set;



public abstract class AbstractStandardTaskGuideFacadeControllerBean extends AbstractBizControllerBean implements StandardTaskGuideFacadeController
{
    protected AbstractStandardTaskGuideFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("0C0072DE");
    }

    public void standardTaskGuideDel(Context ctx, List list) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("5bcf1b8b-192d-441a-88cf-9f1cc19454a1"), new Object[]{ctx, list});
            invokeServiceBefore(svcCtx);
            _standardTaskGuideDel(ctx, list);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _standardTaskGuideDel(Context ctx, List list) throws BOSException;

    public Set getAllId(Context ctx, List list) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f5fe9d02-b870-4701-9ba1-b4078a8faaa8"), new Object[]{ctx, list});
            invokeServiceBefore(svcCtx);
            Set retValue = (Set)_getAllId(ctx, list);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Set _getAllId(Context ctx, List list) throws BOSException;

}