package com.kingdee.eas.fdc.aimcost.app;

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
import java.lang.Object;
import java.math.BigDecimal;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.aimcost.MeasureCostCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.aimcost.MeasureCostInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.bos.util.BOSUuid;



public abstract class AbstractMeasureCostControllerBean extends FDCBillControllerBean implements MeasureCostController
{
    protected AbstractMeasureCostControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("99193494");
    }

    public MeasureCostInfo getMeasureCostInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c8cc3442-010d-1000-e000-0002c0a813e8"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            MeasureCostInfo retValue = (MeasureCostInfo)_getValue(ctx, pk);
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

    public MeasureCostInfo getMeasureCostInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c8cc3442-010d-1000-e000-0003c0a813e8"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            MeasureCostInfo retValue = (MeasureCostInfo)_getValue(ctx, pk, selector);
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

    public MeasureCostCollection getMeasureCostCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c8cc3442-010d-1000-e000-0004c0a813e8"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            MeasureCostCollection retValue = (MeasureCostCollection)_getCollection(ctx, svcCtx, view);
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

    public MeasureCostCollection getMeasureCostCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c8cc3442-010d-1000-e000-0005c0a813e8"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            MeasureCostCollection retValue = (MeasureCostCollection)_getCollection(ctx, svcCtx);
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

    public MeasureCostCollection getMeasureCostCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c8cc3442-010d-1000-e000-0006c0a813e8"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            MeasureCostCollection retValue = (MeasureCostCollection)_getCollection(ctx, svcCtx, oql);
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

    public String storeFromTemplate(Context ctx, String id, String orgId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("113e9d97-0115-1000-e000-003fc0a81296"), new Object[]{ctx, id, orgId});
            invokeServiceBefore(svcCtx);
            String retValue = (String)_storeFromTemplate(ctx, id, orgId);
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
    protected abstract String _storeFromTemplate(Context ctx, String id, String orgId) throws BOSException, EASBizException;

    public void storeToTmplate(Context ctx, String id, boolean isTemplate) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("2052b811-0115-1000-e000-0013c0a81296"), new Object[]{ctx, id, new Boolean(isTemplate)});
            invokeServiceBefore(svcCtx);
            _storeToTmplate(ctx, id, isTemplate);
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
    protected abstract void _storeToTmplate(Context ctx, String id, boolean isTemplate) throws BOSException, EASBizException;

    public void storeToTemplate(Context ctx, String id, boolean isTemplate) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("b2743eb2-0115-1000-e000-0004c0a81296"), new Object[]{ctx, id, new Boolean(isTemplate)});
            invokeServiceBefore(svcCtx);
            _storeToTemplate(ctx, id, isTemplate);
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
    protected abstract void _storeToTemplate(Context ctx, String id, boolean isTemplate) throws BOSException, EASBizException;

    public void exportIndex(Context ctx, BOSUuid id) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("460896f2-0116-1000-e000-0052c0a81296"), new Object[]{ctx, id});
            invokeServiceBefore(svcCtx);
            _exportIndex(ctx, id);
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
    protected abstract void _exportIndex(Context ctx, BOSUuid id) throws BOSException, EASBizException;

    public void exportAimCost(Context ctx, String measureId, String projectId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("871249d1-0117-1000-e000-0014c0a81296"), new Object[]{ctx, measureId, projectId});
            invokeServiceBefore(svcCtx);
            _exportAimCost(ctx, measureId, projectId);
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
    protected abstract void _exportAimCost(Context ctx, String measureId, String projectId) throws BOSException, EASBizException;

    public MeasureCostInfo getMeasureFromTemplate(Context ctx, String templateId, String orgId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("87b22af1-0118-1000-e000-0004c0a812a0"), new Object[]{ctx, templateId, orgId});
            invokeServiceBefore(svcCtx);
            MeasureCostInfo retValue = (MeasureCostInfo)_getMeasureFromTemplate(ctx, templateId, orgId);
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
    protected abstract IObjectValue _getMeasureFromTemplate(Context ctx, String templateId, String orgId) throws BOSException, EASBizException;

    public Map getImportData(Context ctx, Map params) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("0e17c96c-011a-1000-e000-0004c0a812a0"), new Object[]{ctx, params});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getImportData(ctx, params);
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
    protected abstract Map _getImportData(Context ctx, Map params) throws BOSException, EASBizException;

    public Object getTemplateDataStream(Context ctx) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("7565b7b2-f126-4d1b-9c1b-7a50e88d9801"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            Object retValue = (Object)_getTemplateDataStream(ctx);
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
    protected abstract Object _getTemplateDataStream(Context ctx) throws BOSException, EASBizException;

    public Map getMeasureRptData(Context ctx, Map params) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("1fdd33fd-0f81-4060-9aa3-d629fbd174b1"), new Object[]{ctx, params});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getMeasureRptData(ctx, params);
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
    protected abstract Map _getMeasureRptData(Context ctx, Map params) throws BOSException, EASBizException;

    public void reverseWriteProject(Context ctx, String measureId, String targetPrjId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f8a371c9-6988-47ef-b666-6651c22efe3b"), new Object[]{ctx, measureId, targetPrjId});
            invokeServiceBefore(svcCtx);
            _reverseWriteProject(ctx, measureId, targetPrjId);
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
    protected abstract void _reverseWriteProject(Context ctx, String measureId, String targetPrjId) throws BOSException, EASBizException;

    public void costSycMeasureIncome(Context ctx, String measureId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e3a0bc37-dab8-46ca-b21b-525577b4de78"), new Object[]{ctx, measureId});
            invokeServiceBefore(svcCtx);
            _costSycMeasureIncome(ctx, measureId);
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
    protected abstract void _costSycMeasureIncome(Context ctx, String measureId) throws BOSException, EASBizException;

    public void sysProduct(Context ctx, Map param) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("28c09fad-6431-4bb4-9242-7a1f99f24dc7"), new Object[]{ctx, param});
            invokeServiceBefore(svcCtx);
            _sysProduct(ctx, param);
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
    protected abstract void _sysProduct(Context ctx, Map param) throws BOSException, EASBizException;

    public double getIncreaseRate(Context ctx, String billid, String costaccountLongNumber, String projectid) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("ac8a403c-9da9-4557-ad00-49b3a1870bef"), new Object[]{ctx, billid, costaccountLongNumber, projectid});
            invokeServiceBefore(svcCtx);
            double retValue = (double)_getIncreaseRate(ctx, billid, costaccountLongNumber, projectid);
            svcCtx.setMethodReturnValue(new Double(retValue));
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract double _getIncreaseRate(Context ctx, String billid, String costaccountLongNumber, String projectid) throws BOSException;

    public BigDecimal getTotalCostAccount(Context ctx, BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("ac24ab80-565f-452b-acb0-760d5145a0e3"), new Object[]{ctx, billId});
            invokeServiceBefore(svcCtx);
            BigDecimal retValue = (BigDecimal)_getTotalCostAccount(ctx, billId);
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
    protected abstract BigDecimal _getTotalCostAccount(Context ctx, BOSUuid billId) throws BOSException, EASBizException;

    public BigDecimal getLastTotalCostAccount(Context ctx, BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("36a778e8-18e3-451d-9356-e73345fdf716"), new Object[]{ctx, billId});
            invokeServiceBefore(svcCtx);
            BigDecimal retValue = (BigDecimal)_getLastTotalCostAccount(ctx, billId);
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
    protected abstract BigDecimal _getLastTotalCostAccount(Context ctx, BOSUuid billId) throws BOSException, EASBizException;

    public FDCBillCollection getFDCBillCollection (Context ctx) throws BOSException
    {
    	return (FDCBillCollection)(getMeasureCostCollection(ctx).cast(FDCBillCollection.class));
    }
    public FDCBillCollection getFDCBillCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (FDCBillCollection)(getMeasureCostCollection(ctx, view).cast(FDCBillCollection.class));
    }
    public FDCBillCollection getFDCBillCollection (Context ctx, String oql) throws BOSException
    {
    	return (FDCBillCollection)(getMeasureCostCollection(ctx, oql).cast(FDCBillCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBillBaseCollection)(getMeasureCostCollection(ctx).cast(CoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBillBaseCollection)(getMeasureCostCollection(ctx, view).cast(CoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBillBaseCollection)(getMeasureCostCollection(ctx, oql).cast(CoreBillBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx) throws BOSException
    {
    	return (ObjectBaseCollection)(getMeasureCostCollection(ctx).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (ObjectBaseCollection)(getMeasureCostCollection(ctx, view).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (ObjectBaseCollection)(getMeasureCostCollection(ctx, oql).cast(ObjectBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getMeasureCostCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getMeasureCostCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getMeasureCostCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}