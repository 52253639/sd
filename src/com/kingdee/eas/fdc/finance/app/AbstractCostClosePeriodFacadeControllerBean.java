package com.kingdee.eas.fdc.finance.app;

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
import java.util.List;



public abstract class AbstractCostClosePeriodFacadeControllerBean extends AbstractBizControllerBean implements CostClosePeriodFacadeController
{
    protected AbstractCostClosePeriodFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("99FC09D7");
    }

    public void tracePayment(Context ctx, List idList) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("7c66cfbe-0118-1000-e000-0003c0a812a1"), new Object[]{ctx, idList});
            invokeServiceBefore(svcCtx);
            _tracePayment(ctx, idList);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _tracePayment(Context ctx, List idList) throws BOSException, EASBizException;

    public void checkCostSplit(Context ctx, List idList) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("7c66cfbe-0118-1000-e000-0004c0a812a1"), new Object[]{ctx, idList});
            invokeServiceBefore(svcCtx);
            _checkCostSplit(ctx, idList);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _checkCostSplit(Context ctx, List idList) throws BOSException, EASBizException;

    public String traceCostClose(Context ctx, List idList) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("7c66cfbe-0118-1000-e000-0005c0a812a1"), new Object[]{ctx, idList});
            invokeServiceBefore(svcCtx);
            String retValue = (String)_traceCostClose(ctx, idList);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract String _traceCostClose(Context ctx, List idList) throws BOSException, EASBizException;

    public void frozenProject(Context ctx, List idList) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8d0d0779-0118-1000-e000-0003c0a812a1"), new Object[]{ctx, idList});
            invokeServiceBefore(svcCtx);
            _frozenProject(ctx, idList);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _frozenProject(Context ctx, List idList) throws BOSException, EASBizException;

    public String antiCostClose(Context ctx, List idList) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8d0d0779-0118-1000-e000-0004c0a812a1"), new Object[]{ctx, idList});
            invokeServiceBefore(svcCtx);
            String retValue = (String)_antiCostClose(ctx, idList);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract String _antiCostClose(Context ctx, List idList) throws BOSException, EASBizException;

    public void delete(Context ctx, String contractId, String periodId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("26894ea9-0119-1000-e000-000ac0a8129f"), new Object[]{ctx, contractId, periodId});
            invokeServiceBefore(svcCtx);
            _delete(ctx, contractId, periodId);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _delete(Context ctx, String contractId, String periodId) throws BOSException, EASBizException;

}