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

import com.kingdee.eas.common.EASBizException;
import java.util.HashMap;
import java.util.HashSet;



public abstract class AbstractFDCScheduleTaskFacadeControllerBean extends AbstractBizControllerBean implements FDCScheduleTaskFacadeController
{
    protected AbstractFDCScheduleTaskFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("CB7402A5");
    }

    public HashMap getMuTaskTreeNodes(Context ctx, HashSet projectNodes) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("02ed6b7c-e86c-4230-8552-9d3cd4f9b19a"), new Object[]{ctx, projectNodes});
            invokeServiceBefore(svcCtx);
            HashMap retValue = (HashMap)_getMuTaskTreeNodes(ctx, projectNodes);
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
    protected abstract HashMap _getMuTaskTreeNodes(Context ctx, HashSet projectNodes) throws BOSException, EASBizException;

}