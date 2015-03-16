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

public class TranCustomerEntry extends CoreBase implements ITranCustomerEntry
{
    public TranCustomerEntry()
    {
        super();
        registerInterface(ITranCustomerEntry.class, this);
    }
    public TranCustomerEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ITranCustomerEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("912E184E");
    }
    private TranCustomerEntryController getController() throws BOSException
    {
        return (TranCustomerEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public TranCustomerEntryInfo getTranCustomerEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTranCustomerEntryInfo(getContext(), pk);
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
    public TranCustomerEntryInfo getTranCustomerEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTranCustomerEntryInfo(getContext(), pk, selector);
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
    public TranCustomerEntryInfo getTranCustomerEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTranCustomerEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public TranCustomerEntryCollection getTranCustomerEntryCollection() throws BOSException
    {
        try {
            return getController().getTranCustomerEntryCollection(getContext());
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
    public TranCustomerEntryCollection getTranCustomerEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTranCustomerEntryCollection(getContext(), view);
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
    public TranCustomerEntryCollection getTranCustomerEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getTranCustomerEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}