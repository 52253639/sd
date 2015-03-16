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

public class SignPayListEntry extends TranPayListEntry implements ISignPayListEntry
{
    public SignPayListEntry()
    {
        super();
        registerInterface(ISignPayListEntry.class, this);
    }
    public SignPayListEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ISignPayListEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("94DD95E4");
    }
    private SignPayListEntryController getController() throws BOSException
    {
        return (SignPayListEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public SignPayListEntryInfo getSignPayListEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSignPayListEntryInfo(getContext(), pk);
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
    public SignPayListEntryInfo getSignPayListEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSignPayListEntryInfo(getContext(), pk, selector);
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
    public SignPayListEntryInfo getSignPayListEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSignPayListEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public SignPayListEntryCollection getSignPayListEntryCollection() throws BOSException
    {
        try {
            return getController().getSignPayListEntryCollection(getContext());
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
    public SignPayListEntryCollection getSignPayListEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSignPayListEntryCollection(getContext(), view);
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
    public SignPayListEntryCollection getSignPayListEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getSignPayListEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}