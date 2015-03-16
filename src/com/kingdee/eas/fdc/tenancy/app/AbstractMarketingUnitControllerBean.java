package com.kingdee.eas.fdc.tenancy.app;

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

import com.kingdee.eas.framework.app.TreeBaseControllerBean;
import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.tenancy.MarketingUnitInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.eas.framework.TreeBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitCollection;
import com.kingdee.eas.base.permission.UserInfo;
import java.util.ArrayList;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.framework.DataBaseCollection;



public abstract class AbstractMarketingUnitControllerBean extends TreeBaseControllerBean implements MarketingUnitController
{
    protected AbstractMarketingUnitControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("1C059DC1");
    }

    public MarketingUnitInfo getMarketingUnitInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("9f68332b-7880-4ae0-b7b2-dcc3cdf77368"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            MarketingUnitInfo retValue = (MarketingUnitInfo)_getValue(ctx, pk);
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

    public MarketingUnitInfo getMarketingUnitInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a8bed940-fba5-40cb-b00e-6241695a6c80"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            MarketingUnitInfo retValue = (MarketingUnitInfo)_getValue(ctx, pk, selector);
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

    public MarketingUnitInfo getMarketingUnitInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c56a3b4a-9bdb-4300-b88a-3bb0ecb5347f"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            MarketingUnitInfo retValue = (MarketingUnitInfo)_getValue(ctx, oql);
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

    public MarketingUnitCollection getMarketingUnitCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("5b96663c-9bcd-405b-83cf-79e784e5839a"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            MarketingUnitCollection retValue = (MarketingUnitCollection)_getCollection(ctx, svcCtx);
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

    public MarketingUnitCollection getMarketingUnitCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("6498943a-47e0-4eab-b667-dc2b4d9da390"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            MarketingUnitCollection retValue = (MarketingUnitCollection)_getCollection(ctx, svcCtx, view);
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

    public MarketingUnitCollection getMarketingUnitCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f3d83be5-8776-4466-8718-587bec4f36ce"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            MarketingUnitCollection retValue = (MarketingUnitCollection)_getCollection(ctx, svcCtx, oql);
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

    public Set getPermitSaleManIdSet(Context ctx, UserInfo currSaleMan) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("fc3c8ce8-a4db-4c7f-baf1-3d88e7129913"), new Object[]{ctx, currSaleMan});
            invokeServiceBefore(svcCtx);
            Set retValue = (Set)_getPermitSaleManIdSet(ctx, currSaleMan);
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
    protected abstract Set _getPermitSaleManIdSet(Context ctx, UserInfo currSaleMan) throws BOSException, EASBizException;

    public String getPermitSaleManIdSql(Context ctx, UserInfo currSaleMan) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("7b5fc293-3479-405c-b9e4-34fcd608f427"), new Object[]{ctx, currSaleMan});
            invokeServiceBefore(svcCtx);
            String retValue = (String)_getPermitSaleManIdSql(ctx, currSaleMan);
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
    protected abstract String _getPermitSaleManIdSql(Context ctx, UserInfo currSaleMan) throws BOSException, EASBizException;

    public Set getPermitSellProjectIdSet(Context ctx, UserInfo saleMan) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("be4416f0-55ab-4800-8afc-04d834c7cbbf"), new Object[]{ctx, saleMan});
            invokeServiceBefore(svcCtx);
            Set retValue = (Set)_getPermitSellProjectIdSet(ctx, saleMan);
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
    protected abstract Set _getPermitSellProjectIdSet(Context ctx, UserInfo saleMan) throws BOSException, EASBizException;

    public String getPermitSellProjectIdSql(Context ctx, UserInfo currSaleMan) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("12a4a0a7-2e89-4de2-b95c-3a92cffc80ab"), new Object[]{ctx, currSaleMan});
            invokeServiceBefore(svcCtx);
            String retValue = (String)_getPermitSellProjectIdSql(ctx, currSaleMan);
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
    protected abstract String _getPermitSellProjectIdSql(Context ctx, UserInfo currSaleMan) throws BOSException, EASBizException;

    public String getPermitFdcCustomerIdSql(Context ctx, UserInfo saleMan, boolean isInCludeDisEnable) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("bf0a7490-e95b-4965-8c59-b31453923b93"), new Object[]{ctx, saleMan, new Boolean(isInCludeDisEnable)});
            invokeServiceBefore(svcCtx);
            String retValue = (String)_getPermitFdcCustomerIdSql(ctx, saleMan, isInCludeDisEnable);
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
    protected abstract String _getPermitFdcCustomerIdSql(Context ctx, UserInfo saleMan, boolean isInCludeDisEnable) throws BOSException, EASBizException;

    public String getPermitFdcCustomerIdSql(Context ctx, UserInfo saleMan, boolean isInCludeDisEnable, boolean isIncludeShared) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3742835b-8e90-4fde-b62c-b7f466859c60"), new Object[]{ctx, saleMan, new Boolean(isInCludeDisEnable), new Boolean(isIncludeShared)});
            invokeServiceBefore(svcCtx);
            String retValue = (String)_getPermitFdcCustomerIdSql(ctx, saleMan, isInCludeDisEnable, isIncludeShared);
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
    protected abstract String _getPermitFdcCustomerIdSql(Context ctx, UserInfo saleMan, boolean isInCludeDisEnable, boolean isIncludeShared) throws BOSException, EASBizException;

    public Set getCustomerIdSetByNameAndPhone(Context ctx, UserInfo saleMan, String customerName, String customerPhone) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("867758b2-677f-4f7b-a0ce-08910be35a31"), new Object[]{ctx, saleMan, customerName, customerPhone});
            invokeServiceBefore(svcCtx);
            Set retValue = (Set)_getCustomerIdSetByNameAndPhone(ctx, saleMan, customerName, customerPhone);
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
    protected abstract Set _getCustomerIdSetByNameAndPhone(Context ctx, UserInfo saleMan, String customerName, String customerPhone) throws BOSException, EASBizException;

    public MarketingUnitCollection getMarketUnitCollByDutySaleMan(Context ctx, UserInfo saleMan) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3ac167b3-ce3b-45ef-9eaa-cd04d7f93a72"), new Object[]{ctx, saleMan});
            invokeServiceBefore(svcCtx);
            MarketingUnitCollection retValue = (MarketingUnitCollection)_getMarketUnitCollByDutySaleMan(ctx, saleMan);
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
    protected abstract MarketingUnitCollection _getMarketUnitCollByDutySaleMan(Context ctx, UserInfo saleMan) throws BOSException, EASBizException;

    public MarketingUnitCollection getMarketUnitCollByMemberSaleMan(Context ctx, UserInfo saleMan) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("adf3fea3-4c73-400c-98e1-a534f0508e09"), new Object[]{ctx, saleMan});
            invokeServiceBefore(svcCtx);
            MarketingUnitCollection retValue = (MarketingUnitCollection)_getMarketUnitCollByMemberSaleMan(ctx, saleMan);
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
    protected abstract MarketingUnitCollection _getMarketUnitCollByMemberSaleMan(Context ctx, UserInfo saleMan) throws BOSException, EASBizException;

    public MarketingUnitCollection getMarketUnitCollByOUIdSet(Context ctx, Set ouIdSet) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("9261bd7b-aeef-4b66-a9d7-8b8ee059f063"), new Object[]{ctx, ouIdSet});
            invokeServiceBefore(svcCtx);
            MarketingUnitCollection retValue = (MarketingUnitCollection)_getMarketUnitCollByOUIdSet(ctx, ouIdSet);
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
    protected abstract MarketingUnitCollection _getMarketUnitCollByOUIdSet(Context ctx, Set ouIdSet) throws BOSException, EASBizException;

    public ArrayList getMuSellProTreeNodes(Context ctx, String detailNodeType, boolean isMuPerm) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("247a2eb8-a418-45bc-a9ef-a83df6fbb0b4"), new Object[]{ctx, detailNodeType, new Boolean(isMuPerm)});
            invokeServiceBefore(svcCtx);
            ArrayList retValue = (ArrayList)_getMuSellProTreeNodes(ctx, detailNodeType, isMuPerm);
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
    protected abstract ArrayList _getMuSellProTreeNodes(Context ctx, String detailNodeType, boolean isMuPerm) throws BOSException, EASBizException;

    public TreeBaseCollection getTreeBaseCollection (Context ctx) throws BOSException
    {
    	return (TreeBaseCollection)(getMarketingUnitCollection(ctx).cast(TreeBaseCollection.class));
    }
    public TreeBaseCollection getTreeBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (TreeBaseCollection)(getMarketingUnitCollection(ctx, view).cast(TreeBaseCollection.class));
    }
    public TreeBaseCollection getTreeBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (TreeBaseCollection)(getMarketingUnitCollection(ctx, oql).cast(TreeBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx) throws BOSException
    {
    	return (DataBaseCollection)(getMarketingUnitCollection(ctx).cast(DataBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (DataBaseCollection)(getMarketingUnitCollection(ctx, view).cast(DataBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (DataBaseCollection)(getMarketingUnitCollection(ctx, oql).cast(DataBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx) throws BOSException
    {
    	return (ObjectBaseCollection)(getMarketingUnitCollection(ctx).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (ObjectBaseCollection)(getMarketingUnitCollection(ctx, view).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (ObjectBaseCollection)(getMarketingUnitCollection(ctx, oql).cast(ObjectBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getMarketingUnitCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getMarketingUnitCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getMarketingUnitCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}