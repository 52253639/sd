package com.kingdee.eas.fdc.contract.programming.app;

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
import com.kingdee.bos.dao.IObjectCollection;
import java.lang.Object;
import java.util.Set;



public abstract class AbstractRenewRelateProgSaveFacadeControllerBean extends AbstractBizControllerBean implements RenewRelateProgSaveFacadeController
{
    protected AbstractRenewRelateProgSaveFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("88151627");
    }

    public void save(Context ctx, IObjectCollection objCol) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("013f3328-94ef-4828-9fbe-24ccfeba33ad"), new Object[]{ctx, objCol});
            invokeServiceBefore(svcCtx);
            _save(ctx, objCol);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _save(Context ctx, IObjectCollection objCol) throws BOSException, EASBizException;

    public Set getContractbillID(Context ctx, Object[] id) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c7ccbd36-14c6-4579-8d61-9a00871acad3"), new Object[]{ctx, id});
            invokeServiceBefore(svcCtx);
            Set retValue = (Set)_getContractbillID(ctx, id);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Set _getContractbillID(Context ctx, Object[] id) throws BOSException;

}