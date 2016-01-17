package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class IntentionCustomer extends FDCBill implements IIntentionCustomer
{
    public IntentionCustomer()
    {
        super();
        registerInterface(IIntentionCustomer.class, this);
    }
    public IntentionCustomer(Context ctx)
    {
        super(ctx);
        registerInterface(IIntentionCustomer.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("75527961");
    }
    private IntentionCustomerController getController() throws BOSException
    {
        return (IntentionCustomerController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public IntentionCustomerInfo getIntentionCustomerInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getIntentionCustomerInfo(getContext(), pk);
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
    public IntentionCustomerInfo getIntentionCustomerInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getIntentionCustomerInfo(getContext(), pk, selector);
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
    public IntentionCustomerInfo getIntentionCustomerInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getIntentionCustomerInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public IntentionCustomerCollection getIntentionCustomerCollection() throws BOSException
    {
        try {
            return getController().getIntentionCustomerCollection(getContext());
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
    public IntentionCustomerCollection getIntentionCustomerCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getIntentionCustomerCollection(getContext(), view);
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
    public IntentionCustomerCollection getIntentionCustomerCollection(String oql) throws BOSException
    {
        try {
            return getController().getIntentionCustomerCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}