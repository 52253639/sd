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
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.TreeBase;

public class BusinessScope extends TreeBase implements IBusinessScope
{
    public BusinessScope()
    {
        super();
        registerInterface(IBusinessScope.class, this);
    }
    public BusinessScope(Context ctx)
    {
        super(ctx);
        registerInterface(IBusinessScope.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("5B406F6B");
    }
    private BusinessScopeController getController() throws BOSException
    {
        return (BusinessScopeController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public BusinessScopeInfo getBusinessScopeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBusinessScopeInfo(getContext(), pk);
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
    public BusinessScopeInfo getBusinessScopeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBusinessScopeInfo(getContext(), pk, selector);
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
    public BusinessScopeInfo getBusinessScopeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBusinessScopeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public BusinessScopeCollection getBusinessScopeCollection() throws BOSException
    {
        try {
            return getController().getBusinessScopeCollection(getContext());
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
    public BusinessScopeCollection getBusinessScopeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBusinessScopeCollection(getContext(), view);
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
    public BusinessScopeCollection getBusinessScopeCollection(String oql) throws BOSException
    {
        try {
            return getController().getBusinessScopeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}