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

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import java.util.Date;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.eas.fdc.schedule.MonthScheduleCollection;
import com.kingdee.eas.fdc.schedule.framework.ScheduleBaseCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.base.permission.UserInfo;
import java.sql.ResultSet;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.schedule.framework.app.ScheduleBaseControllerBean;
import com.kingdee.eas.fdc.schedule.MonthScheduleInfo;



public abstract class AbstractMonthScheduleControllerBean extends ScheduleBaseControllerBean implements MonthScheduleController
{
    protected AbstractMonthScheduleControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("C896D921");
    }

    public MonthScheduleInfo getMonthScheduleInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("12dd4c95-0124-1000-e000-0009c0a823d9"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            MonthScheduleInfo retValue = (MonthScheduleInfo)_getValue(ctx, pk);
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

    public MonthScheduleInfo getMonthScheduleInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("12dd4c95-0124-1000-e000-000ac0a823d9"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            MonthScheduleInfo retValue = (MonthScheduleInfo)_getValue(ctx, pk, selector);
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

    public MonthScheduleInfo getMonthScheduleInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("12dd4c95-0124-1000-e000-000bc0a823d9"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            MonthScheduleInfo retValue = (MonthScheduleInfo)_getValue(ctx, oql);
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

    public MonthScheduleCollection getMonthScheduleCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("12dd4c95-0124-1000-e000-000cc0a823d9"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            MonthScheduleCollection retValue = (MonthScheduleCollection)_getCollection(ctx, svcCtx);
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

    public MonthScheduleCollection getMonthScheduleCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("12dd4c95-0124-1000-e000-000dc0a823d9"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            MonthScheduleCollection retValue = (MonthScheduleCollection)_getCollection(ctx, svcCtx, view);
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

    public MonthScheduleCollection getMonthScheduleCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("12dd4c95-0124-1000-e000-000ec0a823d9"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            MonthScheduleCollection retValue = (MonthScheduleCollection)_getCollection(ctx, svcCtx, oql);
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

    public ResultSet fetchTask(Context ctx, String startDate, String endDate, FullOrgUnitInfo adminDept, PersonInfo adminPerson) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("5e6537cd-f593-41c5-bca5-e3ba5762a98a"), new Object[]{ctx, startDate, endDate, adminDept, adminPerson});
            invokeServiceBefore(svcCtx);
            ResultSet retValue = (ResultSet)_fetchTask(ctx, startDate, endDate, adminDept, adminPerson);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract ResultSet _fetchTask(Context ctx, String startDate, String endDate, IObjectValue adminDept, IObjectValue adminPerson) throws BOSException, EASBizException;

    public FDCScheduleTaskCollection fetchTask0(Context ctx, Date start, Date end, String adminDeptID, PersonInfo adminPerson, boolean isThisMonth, CurProjectInfo curProject) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("da589124-0718-4d39-b2d0-028d6ebe3426"), new Object[]{ctx, start, end, adminDeptID, adminPerson, new Boolean(isThisMonth), curProject});
            invokeServiceBefore(svcCtx);
            FDCScheduleTaskCollection retValue = (FDCScheduleTaskCollection)_fetchTask0(ctx, start, end, adminDeptID, adminPerson, isThisMonth, curProject);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract IObjectCollection _fetchTask0(Context ctx, Date start, Date end, String adminDeptID, IObjectValue adminPerson, boolean isThisMonth, IObjectValue curProject) throws BOSException, EASBizException;

    public Map audit(Context ctx, Set ids, UserInfo auditor) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("76512d7e-d52d-4eda-be0c-5aded44f3a96"), new Object[]{ctx, ids, auditor});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_audit(ctx, ids, auditor);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Map _audit(Context ctx, Set ids, UserInfo auditor) throws BOSException, EASBizException;

    public Map unAudit(Context ctx, Set ids, UserInfo unAduitor) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("1a8f0e41-2524-4612-8739-59c93367e6a4"), new Object[]{ctx, ids, unAduitor});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_unAudit(ctx, ids, unAduitor);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Map _unAudit(Context ctx, Set ids, UserInfo unAduitor) throws BOSException, EASBizException;

    public void setAudittingStatus(Context ctx, BOSUuid billId) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("334995c2-c56b-4618-bfe6-f2a8bca61512"), new Object[]{ctx, billId});
            invokeServiceBefore(svcCtx);
            _setAudittingStatus(ctx, billId);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _setAudittingStatus(Context ctx, BOSUuid billId) throws BOSException;

    public void setSubmitStatus(Context ctx, BOSUuid billId) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("07a969b5-8f4b-4867-89c0-8f2babe3d715"), new Object[]{ctx, billId});
            invokeServiceBefore(svcCtx);
            _setSubmitStatus(ctx, billId);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _setSubmitStatus(Context ctx, BOSUuid billId) throws BOSException;

    public void unAudit(Context ctx, BOSUuid billID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("901fda27-d762-408e-a25a-c2595c979ee2"), new Object[]{ctx, billID});
            invokeServiceBefore(svcCtx);
            _unAudit(ctx, billID);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _unAudit(Context ctx, BOSUuid billID) throws BOSException, EASBizException;

    public void audit(Context ctx, BOSUuid billID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("5fc9172f-e30c-44ad-a556-975f8dfacb38"), new Object[]{ctx, billID});
            invokeServiceBefore(svcCtx);
            _audit(ctx, billID);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _audit(Context ctx, BOSUuid billID) throws BOSException, EASBizException;

    public ScheduleBaseCollection getScheduleBaseCollection (Context ctx) throws BOSException
    {
    	return (ScheduleBaseCollection)(getMonthScheduleCollection(ctx).cast(ScheduleBaseCollection.class));
    }
    public ScheduleBaseCollection getScheduleBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (ScheduleBaseCollection)(getMonthScheduleCollection(ctx, view).cast(ScheduleBaseCollection.class));
    }
    public ScheduleBaseCollection getScheduleBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (ScheduleBaseCollection)(getMonthScheduleCollection(ctx, oql).cast(ScheduleBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBillBaseCollection)(getMonthScheduleCollection(ctx).cast(CoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBillBaseCollection)(getMonthScheduleCollection(ctx, view).cast(CoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBillBaseCollection)(getMonthScheduleCollection(ctx, oql).cast(CoreBillBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx) throws BOSException
    {
    	return (ObjectBaseCollection)(getMonthScheduleCollection(ctx).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (ObjectBaseCollection)(getMonthScheduleCollection(ctx, view).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (ObjectBaseCollection)(getMonthScheduleCollection(ctx, oql).cast(ObjectBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getMonthScheduleCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getMonthScheduleCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getMonthScheduleCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}