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

public class MarketSupplierStockLinkPerson extends CoreBillEntryBase implements IMarketSupplierStockLinkPerson
{
    public MarketSupplierStockLinkPerson()
    {
        super();
        registerInterface(IMarketSupplierStockLinkPerson.class, this);
    }
    public MarketSupplierStockLinkPerson(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketSupplierStockLinkPerson.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9BB9DD8F");
    }
    private MarketSupplierStockLinkPersonController getController() throws BOSException
    {
        return (MarketSupplierStockLinkPersonController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public MarketSupplierStockLinkPersonInfo getMarketSupplierStockLinkPersonInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierStockLinkPersonInfo(getContext(), pk);
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
    public MarketSupplierStockLinkPersonInfo getMarketSupplierStockLinkPersonInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierStockLinkPersonInfo(getContext(), pk, selector);
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
    public MarketSupplierStockLinkPersonInfo getMarketSupplierStockLinkPersonInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierStockLinkPersonInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public MarketSupplierStockLinkPersonCollection getMarketSupplierStockLinkPersonCollection() throws BOSException
    {
        try {
            return getController().getMarketSupplierStockLinkPersonCollection(getContext());
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
    public MarketSupplierStockLinkPersonCollection getMarketSupplierStockLinkPersonCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketSupplierStockLinkPersonCollection(getContext(), view);
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
    public MarketSupplierStockLinkPersonCollection getMarketSupplierStockLinkPersonCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketSupplierStockLinkPersonCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}