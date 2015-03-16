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

public class SignCustomerEntry extends TranCustomerEntry implements ISignCustomerEntry
{
    public SignCustomerEntry()
    {
        super();
        registerInterface(ISignCustomerEntry.class, this);
    }
    public SignCustomerEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ISignCustomerEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("6D87EAFC");
    }
    private SignCustomerEntryController getController() throws BOSException
    {
        return (SignCustomerEntryController)getBizController();
    }
    /**
     *getCollection-System defined method
     *@param view view
     *@return
     */
    public SignCustomerEntryCollection getSignCustomerEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSignCustomerEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public SignCustomerEntryCollection getSignCustomerEntryCollection() throws BOSException
    {
        try {
            return getController().getSignCustomerEntryCollection(getContext());
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
    public SignCustomerEntryCollection getSignCustomerEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getSignCustomerEntryCollection(getContext(), oql);
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
    public SignCustomerEntryInfo getSignCustomerEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSignCustomerEntryInfo(getContext(), pk);
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
    public SignCustomerEntryInfo getSignCustomerEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSignCustomerEntryInfo(getContext(), pk, selector);
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
    public SignCustomerEntryInfo getSignCustomerEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSignCustomerEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}