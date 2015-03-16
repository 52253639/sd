package com.kingdee.eas.fdc.market;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ObjectBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.market.app.*;
import com.kingdee.eas.framework.IObjectBase;

public class MarketChargePaymentEntry extends ObjectBase implements IMarketChargePaymentEntry
{
    public MarketChargePaymentEntry()
    {
        super();
        registerInterface(IMarketChargePaymentEntry.class, this);
    }
    public MarketChargePaymentEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketChargePaymentEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("DE13BF0D");
    }
    private MarketChargePaymentEntryController getController() throws BOSException
    {
        return (MarketChargePaymentEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public MarketChargePaymentEntryInfo getMarketChargePaymentEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketChargePaymentEntryInfo(getContext(), pk);
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
    public MarketChargePaymentEntryInfo getMarketChargePaymentEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketChargePaymentEntryInfo(getContext(), pk, selector);
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
    public MarketChargePaymentEntryInfo getMarketChargePaymentEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketChargePaymentEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public MarketChargePaymentEntryCollection getMarketChargePaymentEntryCollection() throws BOSException
    {
        try {
            return getController().getMarketChargePaymentEntryCollection(getContext());
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
    public MarketChargePaymentEntryCollection getMarketChargePaymentEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketChargePaymentEntryCollection(getContext(), view);
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
    public MarketChargePaymentEntryCollection getMarketChargePaymentEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketChargePaymentEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}