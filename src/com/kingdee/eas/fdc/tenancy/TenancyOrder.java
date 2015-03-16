package com.kingdee.eas.fdc.tenancy;

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
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public class TenancyOrder extends TenBillBase implements ITenancyOrder
{
    public TenancyOrder()
    {
        super();
        registerInterface(ITenancyOrder.class, this);
    }
    public TenancyOrder(Context ctx)
    {
        super(ctx);
        registerInterface(ITenancyOrder.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("FA35C857");
    }
    private TenancyOrderController getController() throws BOSException
    {
        return (TenancyOrderController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public TenancyOrderInfo getTenancyOrderInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyOrderInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public TenancyOrderInfo getTenancyOrderInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyOrderInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql oql
     *@return
     */
    public TenancyOrderInfo getTenancyOrderInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyOrderInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public TenancyOrderCollection getTenancyOrderCollection() throws BOSException
    {
        try {
            return getController().getTenancyOrderCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view view
     *@return
     */
    public TenancyOrderCollection getTenancyOrderCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTenancyOrderCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql oql
     *@return
     */
    public TenancyOrderCollection getTenancyOrderCollection(String oql) throws BOSException
    {
        try {
            return getController().getTenancyOrderCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}