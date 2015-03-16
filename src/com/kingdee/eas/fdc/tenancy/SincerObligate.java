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

public class SincerObligate extends TenBillBase implements ISincerObligate
{
    public SincerObligate()
    {
        super();
        registerInterface(ISincerObligate.class, this);
    }
    public SincerObligate(Context ctx)
    {
        super(ctx);
        registerInterface(ISincerObligate.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("37796DDC");
    }
    private SincerObligateController getController() throws BOSException
    {
        return (SincerObligateController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public SincerObligateInfo getSincerObligateInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSincerObligateInfo(getContext(), pk);
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
    public SincerObligateInfo getSincerObligateInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSincerObligateInfo(getContext(), pk, selector);
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
    public SincerObligateInfo getSincerObligateInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSincerObligateInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SincerObligateCollection getSincerObligateCollection() throws BOSException
    {
        try {
            return getController().getSincerObligateCollection(getContext());
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
    public SincerObligateCollection getSincerObligateCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSincerObligateCollection(getContext(), view);
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
    public SincerObligateCollection getSincerObligateCollection(String oql) throws BOSException
    {
        try {
            return getController().getSincerObligateCollection(getContext(), oql);
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
    /**
     *取消预留-User defined method
     *@param id ID
     *@return
     */
    public boolean cancelSincer(String id) throws BOSException, EASBizException
    {
        try {
            return getController().cancelSincer(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}