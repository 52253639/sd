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

public class NewListTempletColumn extends TreeBase implements INewListTempletColumn
{
    public NewListTempletColumn()
    {
        super();
        registerInterface(INewListTempletColumn.class, this);
    }
    public NewListTempletColumn(Context ctx)
    {
        super(ctx);
        registerInterface(INewListTempletColumn.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("A2976F63");
    }
    private NewListTempletColumnController getController() throws BOSException
    {
        return (NewListTempletColumnController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public NewListTempletColumnInfo getNewListTempletColumnInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getNewListTempletColumnInfo(getContext(), pk);
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
    public NewListTempletColumnInfo getNewListTempletColumnInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getNewListTempletColumnInfo(getContext(), pk, selector);
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
    public NewListTempletColumnInfo getNewListTempletColumnInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getNewListTempletColumnInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public NewListTempletColumnCollection getNewListTempletColumnCollection() throws BOSException
    {
        try {
            return getController().getNewListTempletColumnCollection(getContext());
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
    public NewListTempletColumnCollection getNewListTempletColumnCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getNewListTempletColumnCollection(getContext(), view);
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
    public NewListTempletColumnCollection getNewListTempletColumnCollection(String oql) throws BOSException
    {
        try {
            return getController().getNewListTempletColumnCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}