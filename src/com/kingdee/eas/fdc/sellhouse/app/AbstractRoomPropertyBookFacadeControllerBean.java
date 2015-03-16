package com.kingdee.eas.fdc.sellhouse.app;

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

import com.kingdee.eas.common.EASBizException;
import java.util.Map;
import java.lang.Object;



public abstract class AbstractRoomPropertyBookFacadeControllerBean extends AbstractBizControllerBean implements RoomPropertyBookFacadeController
{
    protected AbstractRoomPropertyBookFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("82CD556E");
    }

    public Map getMutilRoomPropertyCollection(Context ctx, Object selectedObj, Map paramMap) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("6985fb73-3e2b-4d2c-b235-478beb6cac81"), new Object[]{ctx, selectedObj, paramMap});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getMutilRoomPropertyCollection(ctx, selectedObj, paramMap);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Map _getMutilRoomPropertyCollection(Context ctx, Object selectedObj, Map paramMap) throws BOSException, EASBizException;

    public void updateRoomProperty(Context ctx, Map paramMap) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("9d1f83d7-8e76-4553-a122-0a8fe00083c7"), new Object[]{ctx, paramMap});
            invokeServiceBefore(svcCtx);
            _updateRoomProperty(ctx, paramMap);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _updateRoomProperty(Context ctx, Map paramMap) throws BOSException, EASBizException;

    public Map getStepAndMatarilState(Context ctx, Map paramMap) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("66670223-bdf1-48e7-8293-dfcd980271cd"), new Object[]{ctx, paramMap});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getStepAndMatarilState(ctx, paramMap);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Map _getStepAndMatarilState(Context ctx, Map paramMap) throws BOSException, EASBizException;

}