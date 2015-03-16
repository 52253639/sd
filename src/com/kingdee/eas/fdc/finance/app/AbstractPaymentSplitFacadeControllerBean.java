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

import com.kingdee.eas.common.EASBizException;
import java.util.Map;
import com.kingdee.eas.fdc.basedata.ParamValue;
import com.kingdee.eas.fdc.basedata.RetValue;



public abstract class AbstractPaymentSplitFacadeControllerBean extends AbstractBizControllerBean implements PaymentSplitFacadeController
{
    protected AbstractPaymentSplitFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("D11591BD");
    }

    public RetValue getPaymentSplit(Context ctx, ParamValue paramValue) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("86ce98e4-ce3e-4d0c-8c1c-9c1e4ca87df2"), new Object[]{ctx, paramValue});
            invokeServiceBefore(svcCtx);
            RetValue retValue = (RetValue)_getPaymentSplit(ctx, paramValue);
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
    protected abstract RetValue _getPaymentSplit(Context ctx, ParamValue paramValue) throws BOSException, EASBizException;

    public RetValue getWorkLoadConfirmSplitDatas(Context ctx, Map paramValue) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("5fc9bf51-129f-4cbc-824f-b481797bfe7f"), new Object[]{ctx, paramValue});
            invokeServiceBefore(svcCtx);
            RetValue retValue = (RetValue)_getWorkLoadConfirmSplitDatas(ctx, paramValue);
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
    protected abstract RetValue _getWorkLoadConfirmSplitDatas(Context ctx, Map paramValue) throws BOSException, EASBizException;

}