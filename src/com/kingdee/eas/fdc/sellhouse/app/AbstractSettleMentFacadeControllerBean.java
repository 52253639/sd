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
import com.kingdee.bos.dao.IObjectPK;
import java.util.Set;



public abstract class AbstractSettleMentFacadeControllerBean extends AbstractBizControllerBean implements SettleMentFacadeController
{
    protected AbstractSettleMentFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("D528101E");
    }

    public Set dealQuitRoom(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f4d27595-29e6-4ce8-945b-e0960fb33a44"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            Set retValue = (Set)_dealQuitRoom(ctx, pk);
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
    protected abstract Set _dealQuitRoom(Context ctx, IObjectPK pk) throws BOSException, EASBizException;

    public Set dealChangeRoom(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("52a7e500-fc2e-48c3-b33f-78149073aef5"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            Set retValue = (Set)_dealChangeRoom(ctx, pk);
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
    protected abstract Set _dealChangeRoom(Context ctx, IObjectPK pk) throws BOSException, EASBizException;

    public void dealSaleBalance(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3c7a02da-d2e5-4510-aae7-0b3634a42a9a"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            _dealSaleBalance(ctx, pk);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _dealSaleBalance(Context ctx, IObjectPK pk) throws BOSException, EASBizException;

    public void dealAntiSaleBalance(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("77809287-5a91-4647-90ef-12822b689bcf"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            _dealAntiSaleBalance(ctx, pk);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _dealAntiSaleBalance(Context ctx, IObjectPK pk) throws BOSException, EASBizException;

}