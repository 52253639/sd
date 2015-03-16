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

public class SincerityPurchaseCustomerEntry extends TranCustomerEntry implements ISincerityPurchaseCustomerEntry
{
    public SincerityPurchaseCustomerEntry()
    {
        super();
        registerInterface(ISincerityPurchaseCustomerEntry.class, this);
    }
    public SincerityPurchaseCustomerEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ISincerityPurchaseCustomerEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("282C39B8");
    }
    private SincerityPurchaseCustomerEntryController getController() throws BOSException
    {
        return (SincerityPurchaseCustomerEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public SincerityPurchaseCustomerEntryInfo getSincerityPurchaseCustomerEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSincerityPurchaseCustomerEntryInfo(getContext(), pk);
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
    public SincerityPurchaseCustomerEntryInfo getSincerityPurchaseCustomerEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSincerityPurchaseCustomerEntryInfo(getContext(), pk, selector);
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
    public SincerityPurchaseCustomerEntryInfo getSincerityPurchaseCustomerEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSincerityPurchaseCustomerEntryInfo(getContext(), oql);
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
    public SincerityPurchaseCustomerEntryCollection getSincerityPurchaseCustomerEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSincerityPurchaseCustomerEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public SincerityPurchaseCustomerEntryCollection getSincerityPurchaseCustomerEntryCollection() throws BOSException
    {
        try {
            return getController().getSincerityPurchaseCustomerEntryCollection(getContext());
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
    public SincerityPurchaseCustomerEntryCollection getSincerityPurchaseCustomerEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getSincerityPurchaseCustomerEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}