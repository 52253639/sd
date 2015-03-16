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

public class PurCustomerEntry extends TranCustomerEntry implements IPurCustomerEntry
{
    public PurCustomerEntry()
    {
        super();
        registerInterface(IPurCustomerEntry.class, this);
    }
    public PurCustomerEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IPurCustomerEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9727BE02");
    }
    private PurCustomerEntryController getController() throws BOSException
    {
        return (PurCustomerEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public PurCustomerEntryInfo getPurCustomerEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPurCustomerEntryInfo(getContext(), pk);
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
    public PurCustomerEntryInfo getPurCustomerEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPurCustomerEntryInfo(getContext(), pk, selector);
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
    public PurCustomerEntryInfo getPurCustomerEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPurCustomerEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public PurCustomerEntryCollection getPurCustomerEntryCollection() throws BOSException
    {
        try {
            return getController().getPurCustomerEntryCollection(getContext());
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
    public PurCustomerEntryCollection getPurCustomerEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPurCustomerEntryCollection(getContext(), view);
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
    public PurCustomerEntryCollection getPurCustomerEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getPurCustomerEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}