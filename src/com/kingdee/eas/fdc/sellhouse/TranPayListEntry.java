package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public class TranPayListEntry extends CoreBase implements ITranPayListEntry
{
    public TranPayListEntry()
    {
        super();
        registerInterface(ITranPayListEntry.class, this);
    }
    public TranPayListEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ITranPayListEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("53F37652");
    }
    private TranPayListEntryController getController() throws BOSException
    {
        return (TranPayListEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public TranPayListEntryInfo getTranPayListEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTranPayListEntryInfo(getContext(), pk);
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
    public TranPayListEntryInfo getTranPayListEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTranPayListEntryInfo(getContext(), pk, selector);
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
    public TranPayListEntryInfo getTranPayListEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTranPayListEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public TranPayListEntryCollection getTranPayListEntryCollection() throws BOSException
    {
        try {
            return getController().getTranPayListEntryCollection(getContext());
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
    public TranPayListEntryCollection getTranPayListEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTranPayListEntryCollection(getContext(), view);
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
    public TranPayListEntryCollection getTranPayListEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getTranPayListEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}