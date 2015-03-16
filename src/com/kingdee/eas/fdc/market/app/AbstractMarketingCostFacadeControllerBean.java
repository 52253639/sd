package com.kingdee.eas.fdc.market.app;

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
import com.kingdee.eas.framework.report.app.VirtualRptBaseFacadeControllerBean;
import com.kingdee.eas.base.param.ParamException;
import com.kingdee.eas.framework.report.util.RptParams;



public abstract class AbstractMarketingCostFacadeControllerBean extends VirtualRptBaseFacadeControllerBean implements MarketingCostFacadeController
{
    protected AbstractMarketingCostFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("1D8FAC1C");
    }

    public RptParams getTableList(Context ctx, RptParams params, String ids) throws BOSException, ParamException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3bbe101a-d62e-4715-b5e8-a3b26e29a561"), new Object[]{ctx, params, ids});
            invokeServiceBefore(svcCtx);
            RptParams retValue = (RptParams)_getTableList(ctx, params, ids);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } catch (ParamException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract RptParams _getTableList(Context ctx, RptParams params, String ids) throws BOSException, ParamException;

}