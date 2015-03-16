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

public class AttachRentAdjust extends TenBillBase implements IAttachRentAdjust
{
    public AttachRentAdjust()
    {
        super();
        registerInterface(IAttachRentAdjust.class, this);
    }
    public AttachRentAdjust(Context ctx)
    {
        super(ctx);
        registerInterface(IAttachRentAdjust.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3B7A27F6");
    }
    private AttachRentAdjustController getController() throws BOSException
    {
        return (AttachRentAdjustController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public AttachRentAdjustInfo getAttachRentAdjustInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getAttachRentAdjustInfo(getContext(), pk);
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
    public AttachRentAdjustInfo getAttachRentAdjustInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getAttachRentAdjustInfo(getContext(), pk, selector);
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
    public AttachRentAdjustInfo getAttachRentAdjustInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getAttachRentAdjustInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public AttachRentAdjustCollection getAttachRentAdjustCollection() throws BOSException
    {
        try {
            return getController().getAttachRentAdjustCollection(getContext());
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
    public AttachRentAdjustCollection getAttachRentAdjustCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getAttachRentAdjustCollection(getContext(), view);
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
    public AttachRentAdjustCollection getAttachRentAdjustCollection(String oql) throws BOSException
    {
        try {
            return getController().getAttachRentAdjustCollection(getContext(), oql);
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