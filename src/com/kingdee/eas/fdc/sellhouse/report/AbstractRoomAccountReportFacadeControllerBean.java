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



public abstract class AbstractRoomAccountReportFacadeControllerBean extends CommRptBaseControllerBean implements RoomAccountReportFacadeController
{
    protected AbstractRoomAccountReportFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("79C7C914");
    }

    public IRowSet getPrintData(Context ctx, Set idSet) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("2724911f-986a-41ec-a5d8-bcfba18fb707"), new Object[]{ctx, idSet});
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