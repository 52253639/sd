package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public class PurchaseAgioEntry extends AgioEntry implements IPurchaseAgioEntry
{
    public PurchaseAgioEntry()
    {
        super();
        registerInterface(IPurchaseAgioEntry.class, this);
    }
    public PurchaseAgioEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IPurchaseAgioEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("7FEF668A");
    }
    private PurchaseAgioEntryController getController() throws BOSException
    {
        return (PurchaseAgioEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public PurchaseAgioEntryInfo getPurchaseAgioEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPurchaseAgioEntryInfo(getContext(), pk);
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
    public PurchaseAgioEntryInfo getPurchaseAgioEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPurchaseAgioEntryInfo(getContext(), oql);
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
    public PurchaseAgioEntryInfo getPurchaseAgioEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPurchaseAgioEntryInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public PurchaseAgioEntryCollection getPurchaseAgioEntryCollection() throws BOSException
    {
        try {
            return getController().getPurchaseAgioEntryCollection(getContext());
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
    public PurchaseAgioEntryCollection getPurchaseAgioEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPurchaseAgioEntryCollection(getContext(), view);
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
    public PurchaseAgioEntryCollection getPurchaseAgioEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getPurchaseAgioEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}