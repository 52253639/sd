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

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.jdbc.rowset.IRowSet;



public abstract class AbstractFinanceSubscribeUIFacadeControllerBean extends AbstractBizControllerBean implements FinanceSubscribeUIFacadeController
{
    protected AbstractFinanceSubscribeUIFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("BCEE00B9");
    }

    public IRowSet getSubscribe(Context ctx, String saleLongNumber, String sellProjectID, String beginQueryDate, String endQueryDate) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("0b1e9a63-311f-42fd-8347-171e5006a6ba"), new Object[]{ctx, saleLongNumber, sellProjectID, beginQueryDate, endQueryDate});
            invokeServiceBefore(svcCtx);
            IRowSet retValue = (IRowSet)_getSubscribe(ctx, saleLongNumber, sellProjectID, beginQueryDate, endQueryDate);
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
    protected abstract IRowSet _getSubscribe(Context ctx, String saleLongNumber, String sellProjectID, String beginQueryDate, String endQueryDate) throws BOSException, EASBizException;

}