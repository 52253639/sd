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

public class PrePurchasePayListEntry extends TranPayListEntry implements IPrePurchasePayListEntry
{
    public PrePurchasePayListEntry()
    {
        super();
        registerInterface(IPrePurchasePayListEntry.class, this);
    }
    public PrePurchasePayListEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IPrePurchasePayListEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("2DDCF055");
    }
    private PrePurchasePayListEntryController getController() throws BOSException
    {
        return (PrePurchasePayListEntryController)getBizController();
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public PrePurchasePayListEntryCollection getPrePurchasePayListEntryCollection() throws BOSException
    {
        try {
            return getController().getPrePurchasePayListEntryCollection(getContext());
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
    public PrePurchasePayListEntryCollection getPrePurchasePayListEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPrePurchasePayListEntryCollection(getContext(), view);
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
    public PrePurchasePayListEntryCollection getPrePurchasePayListEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getPrePurchasePayListEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public PrePurchasePayListEntryInfo getPrePurchasePayListEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPrePurchasePayListEntryInfo(getContext(), pk);
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
    public PrePurchasePayListEntryInfo getPrePurchasePayListEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPrePurchasePayListEntryInfo(getContext(), pk, selector);
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
    public PrePurchasePayListEntryInfo getPrePurchasePayListEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPrePurchasePayListEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *addnew-System defined method
     *@param model model
     *@return
     */
    public IObjectPK addnew(PrePurchasePayListEntryInfo model) throws BOSException, EASBizException
    {
        try {
            return getController().addnew(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}