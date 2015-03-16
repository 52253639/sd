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

public class TenancyRentAdjust extends TenBillBase implements ITenancyRentAdjust
{
    public TenancyRentAdjust()
    {
        super();
        registerInterface(ITenancyRentAdjust.class, this);
    }
    public TenancyRentAdjust(Context ctx)
    {
        super(ctx);
        registerInterface(ITenancyRentAdjust.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("2C5E6CBF");
    }
    private TenancyRentAdjustController getController() throws BOSException
    {
        return (TenancyRentAdjustController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public TenancyRentAdjustInfo getTenancyRentAdjustInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyRentAdjustInfo(getContext(), pk);
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
    public TenancyRentAdjustInfo getTenancyRentAdjustInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyRentAdjustInfo(getContext(), pk, selector);
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
    public TenancyRentAdjustInfo getTenancyRentAdjustInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyRentAdjustInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public TenancyRentAdjustCollection getTenancyRentAdjustCollection() throws BOSException
    {
        try {
            return getController().getTenancyRentAdjustCollection(getContext());
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
    public TenancyRentAdjustCollection getTenancyRentAdjustCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTenancyRentAdjustCollection(getContext(), view);
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
    public TenancyRentAdjustCollection getTenancyRentAdjustCollection(String oql) throws BOSException
    {
        try {
            return getController().getTenancyRentAdjustCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *执行-User defined method
     *@param id ID
     *@return
     */
    public boolean execute(String id) throws BOSException, EASBizException
    {
        try {
            return getController().execute(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}