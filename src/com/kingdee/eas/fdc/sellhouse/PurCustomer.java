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
import com.kingdee.eas.framework.CoreBillBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;

public class PurCustomer extends CoreBillBase implements IPurCustomer
{
    public PurCustomer()
    {
        super();
        registerInterface(IPurCustomer.class, this);
    }
    public PurCustomer(Context ctx)
    {
        super(ctx);
        registerInterface(IPurCustomer.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("33B38C50");
    }
    private PurCustomerController getController() throws BOSException
    {
        return (PurCustomerController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public PurCustomerInfo getPurCustomerInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPurCustomerInfo(getContext(), pk);
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
    public PurCustomerInfo getPurCustomerInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPurCustomerInfo(getContext(), pk, selector);
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
    public PurCustomerInfo getPurCustomerInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPurCustomerInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public PurCustomerCollection getPurCustomerCollection() throws BOSException
    {
        try {
            return getController().getPurCustomerCollection(getContext());
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
    public PurCustomerCollection getPurCustomerCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPurCustomerCollection(getContext(), view);
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
    public PurCustomerCollection getPurCustomerCollection(String oql) throws BOSException
    {
        try {
            return getController().getPurCustomerCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}