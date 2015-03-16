package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.invite.markesupplier.app.*;

public class MarketSupplierStockSupplierServiceType extends CoreBillEntryBase implements IMarketSupplierStockSupplierServiceType
{
    public MarketSupplierStockSupplierServiceType()
    {
        super();
        registerInterface(IMarketSupplierStockSupplierServiceType.class, this);
    }
    public MarketSupplierStockSupplierServiceType(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketSupplierStockSupplierServiceType.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E0A00883");
    }
    private MarketSupplierStockSupplierServiceTypeController getController() throws BOSException
    {
        return (MarketSupplierStockSupplierServiceTypeController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public MarketSupplierStockSupplierServiceTypeInfo getMarketSupplierStockSupplierServiceTypeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierStockSupplierServiceTypeInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public MarketSupplierStockSupplierServiceTypeInfo getMarketSupplierStockSupplierServiceTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierStockSupplierServiceTypeInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public MarketSupplierStockSupplierServiceTypeInfo getMarketSupplierStockSupplierServiceTypeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierStockSupplierServiceTypeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public MarketSupplierStockSupplierServiceTypeCollection getMarketSupplierStockSupplierServiceTypeCollection() throws BOSException
    {
        try {
            return getController().getMarketSupplierStockSupplierServiceTypeCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param view view
     *@return
     */
    public MarketSupplierStockSupplierServiceTypeCollection getMarketSupplierStockSupplierServiceTypeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketSupplierStockSupplierServiceTypeCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param oql oql
     *@return
     */
    public MarketSupplierStockSupplierServiceTypeCollection getMarketSupplierStockSupplierServiceTypeCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketSupplierStockSupplierServiceTypeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}