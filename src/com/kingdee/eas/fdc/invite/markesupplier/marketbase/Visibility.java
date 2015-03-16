package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.DataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class Visibility extends DataBase implements IVisibility
{
    public Visibility()
    {
        super();
        registerInterface(IVisibility.class, this);
    }
    public Visibility(Context ctx)
    {
        super(ctx);
        registerInterface(IVisibility.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("8E5B3F7F");
    }
    private VisibilityController getController() throws BOSException
    {
        return (VisibilityController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public VisibilityInfo getVisibilityInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getVisibilityInfo(getContext(), pk);
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
    public VisibilityInfo getVisibilityInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getVisibilityInfo(getContext(), pk, selector);
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
    public VisibilityInfo getVisibilityInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getVisibilityInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public VisibilityCollection getVisibilityCollection() throws BOSException
    {
        try {
            return getController().getVisibilityCollection(getContext());
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
    public VisibilityCollection getVisibilityCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getVisibilityCollection(getContext(), view);
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
    public VisibilityCollection getVisibilityCollection(String oql) throws BOSException
    {
        try {
            return getController().getVisibilityCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}