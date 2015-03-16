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

public class MarketSupplierStockYearSale extends CoreBillEntryBase implements IMarketSupplierStockYearSale
{
    public MarketSupplierStockYearSale()
    {
        super();
        registerInterface(IMarketSupplierStockYearSale.class, this);
    }
    public MarketSupplierStockYearSale(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketSupplierStockYearSale.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("635547C4");
    }
    private MarketSupplierStockYearSaleController getController() throws BOSException
    {
        return (MarketSupplierStockYearSaleController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public MarketSupplierStockYearSaleInfo getMarketSupplierStockYearSaleInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierStockYearSaleInfo(getContext(), pk);
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
    public MarketSupplierStockYearSaleInfo getMarketSupplierStockYearSaleInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierStockYearSaleInfo(getContext(), pk, selector);
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
    public MarketSupplierStockYearSaleInfo getMarketSupplierStockYearSaleInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierStockYearSaleInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public MarketSupplierStockYearSaleCollection getMarketSupplierStockYearSaleCollection() throws BOSException
    {
        try {
            return getController().getMarketSupplierStockYearSaleCollection(getContext());
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
    public MarketSupplierStockYearSaleCollection getMarketSupplierStockYearSaleCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketSupplierStockYearSaleCollection(getContext(), view);
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
    public MarketSupplierStockYearSaleCollection getMarketSupplierStockYearSaleCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketSupplierStockYearSaleCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}