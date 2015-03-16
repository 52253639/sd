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

public class PurPayListEntry extends TranPayListEntry implements IPurPayListEntry
{
    public PurPayListEntry()
    {
        super();
        registerInterface(IPurPayListEntry.class, this);
    }
    public PurPayListEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IPurPayListEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F0CE637D");
    }
    private PurPayListEntryController getController() throws BOSException
    {
        return (PurPayListEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public PurPayListEntryInfo getPurPayListEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPurPayListEntryInfo(getContext(), pk);
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
    public PurPayListEntryInfo getPurPayListEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPurPayListEntryInfo(getContext(), pk, selector);
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
    public PurPayListEntryInfo getPurPayListEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPurPayListEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public PurPayListEntryCollection getPurPayListEntryCollection() throws BOSException
    {
        try {
            return getController().getPurPayListEntryCollection(getContext());
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
    public PurPayListEntryCollection getPurPayListEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPurPayListEntryCollection(getContext(), view);
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
    public PurPayListEntryCollection getPurPayListEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getPurPayListEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}