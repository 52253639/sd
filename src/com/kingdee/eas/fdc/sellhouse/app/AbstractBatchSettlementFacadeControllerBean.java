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
import java.util.Date;
import com.kingdee.eas.framework.CoreBaseCollection;



public abstract class AbstractBatchSettlementFacadeControllerBean extends AbstractBizControllerBean implements BatchSettlementFacadeController
{
    protected AbstractBatchSettlementFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("0E7E4A82");
    }

    public void generateRecBil(Context ctx, CoreBaseCollection recBillList, CoreBaseCollection payList, Date recDate) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("886fb225-5c9a-4d60-b11d-dcc9ae65c9f6"), new Object[]{ctx, recBillList, payList, recDate});
            invokeServiceBefore(svcCtx);
            _generateRecBil(ctx, recBillList, payList, recDate);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _generateRecBil(Context ctx, IObjectCollection recBillList, IObjectCollection payList, Date recDate) throws BOSException, EASBizException;

}