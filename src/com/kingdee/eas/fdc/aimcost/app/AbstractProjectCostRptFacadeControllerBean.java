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
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.ParamValue;
import java.util.Set;
import com.kingdee.eas.fdc.basedata.RetValue;



public abstract class AbstractProjectCostRptFacadeControllerBean extends AbstractBizControllerBean implements ProjectCostRptFacadeController
{
    protected AbstractProjectCostRptFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("0FC51A21");
    }

    public RetValue getData(Context ctx, ParamValue param) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("4891d975-2545-4d09-add5-8bb37f50bced"), new Object[]{ctx, param});
            invokeServiceBefore(svcCtx);
            RetValue retValue = (RetValue)_getData(ctx, param);
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
    protected abstract RetValue _getData(Context ctx, ParamValue param) throws BOSException, EASBizException;

    public void updateData(Context ctx, String prjId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("9a648c94-95c0-469b-91f7-de5d65fbb4cb"), new Object[]{ctx, prjId});
            invokeServiceBefore(svcCtx);
            _updateData(ctx, prjId);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _updateData(Context ctx, String prjId) throws BOSException, EASBizException;

    public void updateAllData(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("26d817d1-6179-40af-98c9-5d7031d521e0"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            _updateAllData(ctx);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _updateAllData(Context ctx) throws BOSException;

    public void updateProjectsCost(Context ctx, Set prjSet) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("496e9bfa-ae97-4f6d-850e-727586a583bb"), new Object[]{ctx, prjSet});
            invokeServiceBefore(svcCtx);
            _updateProjectsCost(ctx, prjSet);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _updateProjectsCost(Context ctx, Set prjSet) throws BOSException, EASBizException;

    public RetValue getProductSellArea(Context ctx, Set leafPrjIDs) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("0a5a4f3d-5eb8-43c6-a0e8-295c4a53459a"), new Object[]{ctx, leafPrjIDs});
            invokeServiceBefore(svcCtx);
            RetValue retValue = (RetValue)_getProductSellArea(ctx, leafPrjIDs);
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
    protected abstract RetValue _getProductSellArea(Context ctx, Set leafPrjIDs) throws BOSException, EASBizException;

    public RetValue getCollectionProductAimCost(Context ctx, ParamValue paramValue) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("4011964b-93af-43da-b85b-a3d9bd333728"), new Object[]{ctx, paramValue});
            invokeServiceBefore(svcCtx);
            RetValue retValue = (RetValue)_getCollectionProductAimCost(ctx, paramValue);
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
    protected abstract RetValue _getCollectionProductAimCost(Context ctx, ParamValue paramValue) throws BOSException, EASBizException;

    public RetValue getCollectionProductDynAimCost(Context ctx, ParamValue paramValue) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("de5c9307-6699-4a12-8773-fd68f2d25bfb"), new Object[]{ctx, paramValue});
            invokeServiceBefore(svcCtx);
            RetValue retValue = (RetValue)_getCollectionProductDynAimCost(ctx, paramValue);
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
    protected abstract RetValue _getCollectionProductDynAimCost(Context ctx, ParamValue paramValue) throws BOSException, EASBizException;

    public RetValue getCollectionProductDynCost(Context ctx, ParamValue paramValue) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("1130e76e-5352-4693-8d27-723132ad9aea"), new Object[]{ctx, paramValue});
            invokeServiceBefore(svcCtx);
            RetValue retValue = (RetValue)_getCollectionProductDynCost(ctx, paramValue);
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
    protected abstract RetValue _getCollectionProductDynCost(Context ctx, ParamValue paramValue) throws BOSException, EASBizException;

    public RetValue getCollectionFullDynCost(Context ctx, ParamValue paramValue) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d04a7696-55d4-417f-af87-46cb7adaeed5"), new Object[]{ctx, paramValue});
            invokeServiceBefore(svcCtx);
            RetValue retValue = (RetValue)_getCollectionFullDynCost(ctx, paramValue);
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
    protected abstract RetValue _getCollectionFullDynCost(Context ctx, ParamValue paramValue) throws BOSException, EASBizException;

    public RetValue getCollectionContractAcctCost(Context ctx, ParamValue paramValue) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f57d7b18-16a2-4c6d-9eef-48af4c60b7be"), new Object[]{ctx, paramValue});
            invokeServiceBefore(svcCtx);
            RetValue retValue = (RetValue)_getCollectionContractAcctCost(ctx, paramValue);
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
    protected abstract RetValue _getCollectionContractAcctCost(Context ctx, ParamValue paramValue) throws BOSException, EASBizException;

    public RetValue getCollectionContractAcctCostDetails(Context ctx, ParamValue paramValue) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c88a1a90-baa4-4f02-836b-91ee7e4bc0c3"), new Object[]{ctx, paramValue});
            invokeServiceBefore(svcCtx);
            RetValue retValue = (RetValue)_getCollectionContractAcctCostDetails(ctx, paramValue);
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
    protected abstract RetValue _getCollectionContractAcctCostDetails(Context ctx, ParamValue paramValue) throws BOSException, EASBizException;

    public RetValue getProjectCostAnalysis(Context ctx, ParamValue paramValue) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("9a135fd8-5c56-439d-91b6-ea71c7d1cf1e"), new Object[]{ctx, paramValue});
            invokeServiceBefore(svcCtx);
            RetValue retValue = (RetValue)_getProjectCostAnalysis(ctx, paramValue);
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
    protected abstract RetValue _getProjectCostAnalysis(Context ctx, ParamValue paramValue) throws BOSException, EASBizException;

    public RetValue getDynCostInfo(Context ctx, ParamValue param) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("71e61554-2bf0-468d-a5c9-06e3dab9bc66"), new Object[]{ctx, param});
            invokeServiceBefore(svcCtx);
            RetValue retValue = (RetValue)_getDynCostInfo(ctx, param);
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
    protected abstract RetValue _getDynCostInfo(Context ctx, ParamValue param) throws BOSException, EASBizException;

    public RetValue getAimCostDynInfo(Context ctx, ParamValue param) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8e7f3d1b-940c-4e1f-881c-de8a627f4841"), new Object[]{ctx, param});
            invokeServiceBefore(svcCtx);
            RetValue retValue = (RetValue)_getAimCostDynInfo(ctx, param);
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
    protected abstract RetValue _getAimCostDynInfo(Context ctx, ParamValue param) throws BOSException, EASBizException;

}