package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.invite.app.*;
import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.TreeBase;

public class HeadColumn extends TreeBase implements IHeadColumn
{
    public HeadColumn()
    {
        super();
        registerInterface(IHeadColumn.class, this);
    }
    public HeadColumn(Context ctx)
    {
        super(ctx);
        registerInterface(IHeadColumn.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("52E98B7A");
    }
    private HeadColumnController getController() throws BOSException
    {
        return (HeadColumnController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public HeadColumnInfo getHeadColumnInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getHeadColumnInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public HeadColumnInfo getHeadColumnInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getHeadColumnInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql 取值
     *@return
     */
    public HeadColumnInfo getHeadColumnInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getHeadColumnInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public HeadColumnCollection getHeadColumnCollection() throws BOSException
    {
        try {
            return getController().getHeadColumnCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view 取集合
     *@return
     */
    public HeadColumnCollection getHeadColumnCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getHeadColumnCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql 取集合
     *@return
     */
    public HeadColumnCollection getHeadColumnCollection(String oql) throws BOSException
    {
        try {
            return getController().getHeadColumnCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}