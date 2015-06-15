package com.kingdee.eas.fdc.contract.app;

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
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.contract.ContractEvaluationEntryInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.app.CoreBillEntryBaseControllerBean;
import com.kingdee.eas.framework.CoreBillEntryBaseCollection;
import com.kingdee.eas.fdc.contract.ContractEvaluationEntryCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;



public abstract class AbstractContractEvaluationEntryControllerBean extends CoreBillEntryBaseControllerBean implements ContractEvaluationEntryController
{
    protected AbstractContractEvaluationEntryControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("F63B8289");
    }

    public ContractEvaluationEntryInfo getContractEvaluationEntryInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a8a8e60a-5298-443c-84bc-eb26d5bb82fe"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            ContractEvaluationEntryInfo retValue = (ContractEvaluationEntryInfo)_getValue(ctx, pk);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (ContractEvaluationEntryInfo)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectValue _getValue(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        return super._getValue(ctx, pk);
    }

    public ContractEvaluationEntryInfo getContractEvaluationEntryInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("4c52d39f-b8d2-4d8a-8174-5772302aab10"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            ContractEvaluationEntryInfo retValue = (ContractEvaluationEntryInfo)_getValue(ctx, pk, selector);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (ContractEvaluationEntryInfo)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectValue _getValue(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        return super._getValue(ctx, pk, selector);
    }

    public ContractEvaluationEntryInfo getContractEvaluationEntryInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e8abe3ff-ea37-4ac2-962b-c374ad1baee5"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            ContractEvaluationEntryInfo retValue = (ContractEvaluationEntryInfo)_getValue(ctx, oql);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (ContractEvaluationEntryInfo)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectValue _getValue(Context ctx, String oql) throws BOSException, EASBizException
    {
        return super._getValue(ctx, oql);
    }

    public ContractEvaluationEntryCollection getContractEvaluationEntryCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("07f9d33b-d297-44f7-af08-215a181c53f2"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            ContractEvaluationEntryCollection retValue = (ContractEvaluationEntryCollection)_getCollection(ctx, svcCtx);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (ContractEvaluationEntryCollection)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectCollection _getCollection(Context ctx, IServiceContext svcCtx) throws BOSException
    {
        return super._getCollection(ctx, svcCtx);
    }

    public ContractEvaluationEntryCollection getContractEvaluationEntryCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("72c43988-9937-4513-ab03-81018e72dfde"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            ContractEvaluationEntryCollection retValue = (ContractEvaluationEntryCollection)_getCollection(ctx, svcCtx, view);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (ContractEvaluationEntryCollection)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectCollection _getCollection(Context ctx, IServiceContext svcCtx, EntityViewInfo view) throws BOSException
    {
        return super._getCollection(ctx, svcCtx, view);
    }

    public ContractEvaluationEntryCollection getContractEvaluationEntryCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("88240318-0d33-424f-89bc-6bf23b60f239"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            ContractEvaluationEntryCollection retValue = (ContractEvaluationEntryCollection)_getCollection(ctx, svcCtx, oql);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (ContractEvaluationEntryCollection)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectCollection _getCollection(Context ctx, IServiceContext svcCtx, String oql) throws BOSException
    {
        return super._getCollection(ctx, svcCtx, oql);
    }

    public CoreBillEntryBaseCollection getCoreBillEntryBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBillEntryBaseCollection)(getContractEvaluationEntryCollection(ctx).cast(CoreBillEntryBaseCollection.class));
    }
    public CoreBillEntryBaseCollection getCoreBillEntryBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBillEntryBaseCollection)(getContractEvaluationEntryCollection(ctx, view).cast(CoreBillEntryBaseCollection.class));
    }
    public CoreBillEntryBaseCollection getCoreBillEntryBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBillEntryBaseCollection)(getContractEvaluationEntryCollection(ctx, oql).cast(CoreBillEntryBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getContractEvaluationEntryCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getContractEvaluationEntryCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getContractEvaluationEntryCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}