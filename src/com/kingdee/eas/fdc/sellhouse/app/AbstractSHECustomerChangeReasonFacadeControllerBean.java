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
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.sellhouse.SellHouseException;



public abstract class AbstractSHECustomerChangeReasonFacadeControllerBean extends AbstractBizControllerBean implements SHECustomerChangeReasonFacadeController
{
    protected AbstractSHECustomerChangeReasonFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("3B7F1021");
    }

    public void addNewMessage(Context ctx, IObjectValue company, String reason, IObjectValue sheCustomer, IObjectValue propertyConsultant) throws BOSException, SellHouseException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f5fe0bd1-98ae-46bd-831f-2242b884d2f3"), new Object[]{ctx, company, reason, sheCustomer, propertyConsultant});
            invokeServiceBefore(svcCtx);
            _addNewMessage(ctx, company, reason, sheCustomer, propertyConsultant);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } catch (SellHouseException ex0) {
            throw ex0;
        } catch (EASBizException ex1) {
            throw ex1;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _addNewMessage(Context ctx, IObjectValue company, String reason, IObjectValue sheCustomer, IObjectValue propertyConsultant) throws BOSException, SellHouseException, EASBizException;

}