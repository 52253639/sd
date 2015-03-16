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
import java.util.Map;
import java.util.Set;



public abstract class AbstractFDCDepConPayPlanFacadeControllerBean extends AbstractBizControllerBean implements FDCDepConPayPlanFacadeController
{
    protected AbstractFDCDepConPayPlanFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("7916EFE2");
    }

    public Map getProjectPayPlanExeData(Context ctx, Set prjIds, Map param) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("4505b465-0773-4ea7-b956-517ea94f9fec"), new Object[]{ctx, prjIds, param});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getProjectPayPlanExeData(ctx, prjIds, param);
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
    protected abstract Map _getProjectPayPlanExeData(Context ctx, Set prjIds, Map param) throws BOSException, EASBizException;

    public void autoUpdateConPayPlan(Context ctx, String id, boolean isAudit) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("b1c8669a-fe6a-4240-9e50-994f5b4b9bea"), new Object[]{ctx, id, new Boolean(isAudit)});
            invokeServiceBefore(svcCtx);
            _autoUpdateConPayPlan(ctx, id, isAudit);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _autoUpdateConPayPlan(Context ctx, String id, boolean isAudit) throws BOSException, EASBizException;

}