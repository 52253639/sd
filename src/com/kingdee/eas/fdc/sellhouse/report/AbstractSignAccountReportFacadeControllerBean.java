package com.kingdee.eas.fdc.sellhouse.report;

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

import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import java.util.Set;



public abstract class AbstractSignAccountReportFacadeControllerBean extends CommRptBaseControllerBean implements SignAccountReportFacadeController
{
    protected AbstractSignAccountReportFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("1FB12CF2");
    }

    public IRowSet getPrintData(Context ctx, Set idSet) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d8848a7f-7f6b-4c2a-855c-62d432ca8cf1"), new Object[]{ctx, idSet});
            invokeServiceBefore(svcCtx);
            IRowSet retValue = (IRowSet)_getPrintData(ctx, idSet);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract IRowSet _getPrintData(Context ctx, Set idSet) throws BOSException;

}