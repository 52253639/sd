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

public class PrePurchaseCustomerEntry extends TranCustomerEntry implements IPrePurchaseCustomerEntry
{
    public PrePurchaseCustomerEntry()
    {
        super();
        registerInterface(IPrePurchaseCustomerEntry.class, this);
    }
    public PrePurchaseCustomerEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IPrePurchaseCustomerEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F473DEAB");
    }
    private PrePurchaseCustomerEntryController getController() throws BOSException
    {
        return (PrePurchaseCustomerEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public PrePurchaseCustomerEntryInfo getPrePurchaseCustomerEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPrePurchaseCustomerEntryInfo(getContext(), pk);
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
    public PrePurchaseCustomerEntryInfo getPrePurchaseCustomerEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPrePurchaseCustomerEntryInfo(getContext(), pk, selector);
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
    public PrePurchaseCustomerEntryInfo getPrePurchaseCustomerEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPrePurchaseCustomerEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public PrePurchaseCustomerEntryCollection getPrePurchaseCustomerEntryCollection() throws BOSException
    {
        try {
            return getController().getPrePurchaseCustomerEntryCollection(getContext());
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
    public PrePurchaseCustomerEntryCollection getPrePurchaseCustomerEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPrePurchaseCustomerEntryCollection(getContext(), view);
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
    public PrePurchaseCustomerEntryCollection getPrePurchaseCustomerEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getPrePurchaseCustomerEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}