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

public class RentRemission extends TenBillBase implements IRentRemission
{
    public RentRemission()
    {
        super();
        registerInterface(IRentRemission.class, this);
    }
    public RentRemission(Context ctx)
    {
        super(ctx);
        registerInterface(IRentRemission.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("DADF01B7");
    }
    private RentRemissionController getController() throws BOSException
    {
        return (RentRemissionController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public RentRemissionInfo getRentRemissionInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRentRemissionInfo(getContext(), pk);
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
    public RentRemissionInfo getRentRemissionInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRentRemissionInfo(getContext(), pk, selector);
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
    public RentRemissionInfo getRentRemissionInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRentRemissionInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public RentRemissionCollection getRentRemissionCollection() throws BOSException
    {
        try {
            return getController().getRentRemissionCollection(getContext());
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
    public RentRemissionCollection getRentRemissionCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRentRemissionCollection(getContext(), view);
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
    public RentRemissionCollection getRentRemissionCollection(String oql) throws BOSException
    {
        try {
            return getController().getRentRemissionCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}