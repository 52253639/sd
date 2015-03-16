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
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.fdc.finance.SettledMonthlyCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.finance.SettledMonthlyInfo;
import com.kingdee.eas.framework.app.CoreBaseControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.Result;
import com.kingdee.eas.framework.CoreBaseCollection;



public abstract class AbstractSettledMonthlyControllerBean extends CoreBaseControllerBean implements SettledMonthlyController
{
    protected AbstractSettledMonthlyControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("C78A4423");
    }

    public SettledMonthlyInfo getSettledMonthlyInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("72295298-0118-1000-e000-0008c0a812a1"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            SettledMonthlyInfo retValue = (SettledMonthlyInfo)_getValue(ctx, pk);
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
    protected IObjectValue _getValue(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        return super._getValue(ctx, pk);
    }

    public SettledMonthlyInfo getSettledMonthlyInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("72295298-0118-1000-e000-0009c0a812a1"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            SettledMonthlyInfo retValue = (SettledMonthlyInfo)_getValue(ctx, pk, selector);
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
    protected IObjectValue _getValue(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        return super._getValue(ctx, pk, selector);
    }

    public SettledMonthlyInfo getSettledMonthlyInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("72295298-0118-1000-e000-000ac0a812a1"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            SettledMonthlyInfo retValue = (SettledMonthlyInfo)_getValue(ctx, oql);
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
    protected IObjectValue _getValue(Context ctx, String oql) throws BOSException, EASBizException
    {
        return super._getValue(ctx, oql);
    }

    public SettledMonthlyCollection getSettledMonthlyCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("72295298-0118-1000-e000-000bc0a812a1"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            SettledMonthlyCollection retValue = (SettledMonthlyCollection)_getCollection(ctx, svcCtx);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
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

    public SettledMonthlyCollection getSettledMonthlyCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("72295298-0118-1000-e000-000cc0a812a1"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            SettledMonthlyCollection retValue = (SettledMonthlyCollection)_getCollection(ctx, svcCtx, view);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
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

    public SettledMonthlyCollection getSettledMonthlyCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("72295298-0118-1000-e000-000dc0a812a1"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            SettledMonthlyCollection retValue = (SettledMonthlyCollection)_getCollection(ctx, svcCtx, oql);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
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

    public Result addnew(Context ctx, CoreBaseCollection colls) throws BOSException, EASBizException
    {
        CoreBaseInfo model = null;
        boolean isPartSuccess = false;
        boolean isPartFail = false;
        Result rt = new Result();
        for(int i=0; i < colls.size(); i++)
        {
            try 
            {
                model = colls.get(i);
                IObjectPK retValue = addnew(ctx, model);
                LineResult rtl = new LineResult();
                rtl.setPk(retValue);
                rtl.setSucess(true);
                rt.addLineResult(rtl);
                isPartSuccess = true;
                
            }
            catch (BOSException ex) 
            {
                this.setRollbackOnly();
                LineResult rtl = new LineResult();
                rtl.setException(ex);
                rtl.setSucess(false);
                rt.addLineResult(rtl);
            } 
            catch(EASMultiException mulE)
            {
                this.setRollbackOnly();
                LineResult rtl = new LineResult();
                rtl.setException(mulE);
                rtl.setSucess(false);
                rt.addLineResult(rtl);
            }
            catch (EASBizException ex0) 
            {
                this.setRollbackOnly();
                LineResult rtl = new LineResult();
                rtl.setException(ex0);
                rtl.setSucess(false);
                rt.addLineResult(rtl);
            }
        }
        //MultiExceptionUtility.checkMultilException(ctx,ve);
        if(isPartSuccess && isPartFail)
        {
            rt.setResult(Result.PARTSUCCESS);
        }
        else if(isPartSuccess && !isPartFail)
        {
            rt.setResult(Result.ALLSUCCESS);
        }
        else
        {
            rt.setResult(Result.ALLFAIL);
        }
        
        return rt;
    }
    public CoreBaseCollection getCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getSettledMonthlyCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getSettledMonthlyCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getSettledMonthlyCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}