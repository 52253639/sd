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

import com.kingdee.eas.framework.app.TreeBaseControllerBean;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitInfo;
import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.eas.framework.TreeBaseCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
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
        return new BOSObjectType("B6B669CF");
    }

    public MarketingUnitInfo getMarketingUnitInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("ce112474-d2f2-435e-960a-c59524dadd96"), new Object[]{ctx, pk});
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3ddb84ac-30a9-4fe8-9e6a-33ef56f61402"), new Object[]{ctx, pk, selector});
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("dadaddd5-74ec-410d-b4a3-2c0a42f8f69a"), new Object[]{ctx, oql});
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3fc9ec3e-8f02-4c03-9b14-80bfae2122e8"), new Object[]{ctx});
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("32e31f96-7416-4e2b-95f4-c979ec15726d"), new Object[]{ctx, view});
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a837dc1f-53ee-4b53-8a87-2489b4e1a2ce"), new Object[]{ctx, oql});
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

    public Set getPermitSaleManIdSet(Context ctx, UserInfo currSaleMan, IObjectValue sellProInfo) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f0a91c7a-e570-451b-8561-f488e6ead502"), new Object[]{ctx, currSaleMan, sellProInfo});
            invokeServiceBefore(svcCtx);
            Set retValue = (Set)_getPermitSaleManIdSet(ctx, currSaleMan, sellProInfo);
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
    protected abstract Set _getPermitSaleManIdSet(Context ctx, UserInfo currSaleMan, IObjectValue sellProInfo) throws BOSException, EASBizException;

    public String getPermitSaleManIdSql(Context ctx, UserInfo currSaleMan, IObjectValue sellProInfo) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("709a762b-de97-4bb0-9a62-7a454770f486"), new Object[]{ctx, currSaleMan, sellProInfo});
            invokeServiceBefore(svcCtx);
            String retValue = (String)_getPermitSaleManIdSql(ctx, currSaleMan, sellProInfo);
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
    protected abstract String _getPermitSaleManIdSql(Context ctx, UserInfo currSaleMan, IObjectValue sellProInfo) throws BOSException, EASBizException;

    public Set getPermitSellProjectIdSet(Context ctx, UserInfo saleMan) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("723e6c90-e5ef-42eb-96a3-318e5a2b5dff"), new Object[]{ctx, saleMan});
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c3b1a37c-cf09-42a9-bce5-b9a161478ccf"), new Object[]{ctx, currSaleMan});
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

    public Set getSellProjectIdSetAllChild(Context ctx, UserInfo saleMan) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3a164219-e9ba-412f-a1d1-470e21553b9c"), new Object[]{ctx, saleMan});
            invokeServiceBefore(svcCtx);
            Set retValue = (Set)_getSellProjectIdSetAllChild(ctx, saleMan);
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
    protected abstract Set _getSellProjectIdSetAllChild(Context ctx, UserInfo saleMan) throws BOSException, EASBizException;

    public Set getPermitFdcCustomerIdSet(Context ctx, UserInfo saleMan, boolean isInCludeDisEnable) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a0d78dc8-d193-4a5b-8c19-2b59517fdc57"), new Object[]{ctx, saleMan, new Boolean(isInCludeDisEnable)});
            invokeServiceBefore(svcCtx);
            Set retValue = (Set)_getPermitFdcCustomerIdSet(ctx, saleMan, isInCludeDisEnable);
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
    protected abstract Set _getPermitFdcCustomerIdSet(Context ctx, UserInfo saleMan, boolean isInCludeDisEnable) throws BOSException, EASBizException;

    public Set getPermitFdcCustomerIdSet(Context ctx, UserInfo saleMan, boolean isInCludeDisEnable, boolean isIncludeShared) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("bf35b7a2-7eea-4419-9b4f-9b5d0e1f2b6c"), new Object[]{ctx, saleMan, new Boolean(isInCludeDisEnable), new Boolean(isIncludeShared)});
            invokeServiceBefore(svcCtx);
            Set retValue = (Set)_getPermitFdcCustomerIdSet(ctx, saleMan, isInCludeDisEnable, isIncludeShared);
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
    protected abstract Set _getPermitFdcCustomerIdSet(Context ctx, UserInfo saleMan, boolean isInCludeDisEnable, boolean isIncludeShared) throws BOSException, EASBizException;

    public Set getCustomerIdSetByNameAndPhone(Context ctx, UserInfo saleMan, String customerName, String customerPhone) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8eb2cfbe-a009-4820-adb1-f93483372a52"), new Object[]{ctx, saleMan, customerName, customerPhone});
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("1331454d-79c0-4856-ba62-5ab2def3d779"), new Object[]{ctx, saleMan});
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8bdd1d79-e00a-41b0-8e8d-76c63aa09424"), new Object[]{ctx, saleMan});
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("47656b54-1574-40c3-bc7d-629bd4fd9320"), new Object[]{ctx, ouIdSet});
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("383842a3-cc3e-4320-835d-03bdede3736c"), new Object[]{ctx, detailNodeType, new Boolean(isMuPerm)});
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

    public Set checkRefByNext(Context ctx, Set marketingSellProjectSet, MarketingUnitInfo marketingUnitInfo) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("01874ae8-2546-4119-bcc0-0ff75be2ec1d"), new Object[]{ctx, marketingSellProjectSet, marketingUnitInfo});
            invokeServiceBefore(svcCtx);
            Set retValue = (Set)_checkRefByNext(ctx, marketingSellProjectSet, marketingUnitInfo);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Set _checkRefByNext(Context ctx, Set marketingSellProjectSet, MarketingUnitInfo marketingUnitInfo) throws BOSException;

    public boolean checkMemeberOfSameSellPorject(Context ctx, MarketingUnitInfo marketingUnitInfo) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e04ae054-6cf1-416c-84c8-16ac6d719176"), new Object[]{ctx, marketingUnitInfo});
            invokeServiceBefore(svcCtx);
            boolean retValue = (boolean)_checkMemeberOfSameSellPorject(ctx, marketingUnitInfo);
            svcCtx.setMethodReturnValue(new Boolean(retValue));
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
    protected abstract boolean _checkMemeberOfSameSellPorject(Context ctx, MarketingUnitInfo marketingUnitInfo) throws BOSException, EASBizException;

    public MarketingUnitMemberCollection getMarketUnitCollMember(Context ctx, UserInfo saleMans) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("20dec4a7-a5d2-4110-817f-c0864eff99c9"), new Object[]{ctx, saleMans});
            invokeServiceBefore(svcCtx);
            MarketingUnitMemberCollection retValue = (MarketingUnitMemberCollection)_getMarketUnitCollMember(ctx, saleMans);
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
    protected abstract MarketingUnitMemberCollection _getMarketUnitCollMember(Context ctx, UserInfo saleMans) throws BOSException, EASBizException;

    public Set getPermitAllSellProjectIdSet(Context ctx, UserInfo currSaleMan) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f94e6e82-7fd1-4b8b-9862-2c2e7b7631f8"), new Object[]{ctx, currSaleMan});
            invokeServiceBefore(svcCtx);
            Set retValue = (Set)_getPermitAllSellProjectIdSet(ctx, currSaleMan);
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
    protected abstract Set _getPermitAllSellProjectIdSet(Context ctx, UserInfo currSaleMan) throws BOSException, EASBizException;

    public Set getPermitSaleManIdSet(Context ctx, UserInfo currSaleMan) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("5c7fdda6-d982-44ec-a0da-7e04dd6f8ad4"), new Object[]{ctx, currSaleMan});
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("4279b299-c0ac-4f0f-8dc7-b8637ddfbf60"), new Object[]{ctx, currSaleMan});
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