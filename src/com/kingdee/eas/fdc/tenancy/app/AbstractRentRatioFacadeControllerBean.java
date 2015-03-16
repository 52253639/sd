package com.kingdee.eas.fdc.tenancy.app;

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

import java.util.Map;



public abstract class AbstractRentRatioFacadeControllerBean extends AbstractBizControllerBean implements RentRatioFacadeController
{
    protected AbstractRentRatioFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("BB055EC3");
    }

    public Map getRentRatioInfo(Context ctx, Map param) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d5adff79-77b8-40d7-8790-8a2c09d7a811"), new Object[]{ctx, param});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getRentRatioInfo(ctx, param);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Map _getRentRatioInfo(Context ctx, Map param) throws BOSException;

    public Map getBatchFilterInfo(Context ctx, Map customerParams) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e707da77-2fe0-4e5e-98de-b474d11aa52a"), new Object[]{ctx, customerParams});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getBatchFilterInfo(ctx, customerParams);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Map _getBatchFilterInfo(Context ctx, Map customerParams) throws BOSException;

}