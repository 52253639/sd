package com.kingdee.eas.fdc.market;

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
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.market.app.*;
import com.kingdee.eas.framework.TreeBase;

public class StoreSubjectClass extends TreeBase implements IStoreSubjectClass
{
    public StoreSubjectClass()
    {
        super();
        registerInterface(IStoreSubjectClass.class, this);
    }
    public StoreSubjectClass(Context ctx)
    {
        super(ctx);
        registerInterface(IStoreSubjectClass.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3844997C");
    }
    private StoreSubjectClassController getController() throws BOSException
    {
        return (StoreSubjectClassController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public StoreSubjectClassInfo getStoreSubjectClassInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getStoreSubjectClassInfo(getContext(), pk);
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
    public StoreSubjectClassInfo getStoreSubjectClassInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getStoreSubjectClassInfo(getContext(), pk, selector);
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
    public StoreSubjectClassInfo getStoreSubjectClassInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getStoreSubjectClassInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public StoreSubjectClassCollection getStoreSubjectClassCollection() throws BOSException
    {
        try {
            return getController().getStoreSubjectClassCollection(getContext());
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
    public StoreSubjectClassCollection getStoreSubjectClassCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getStoreSubjectClassCollection(getContext(), view);
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
    public StoreSubjectClassCollection getStoreSubjectClassCollection(String oql) throws BOSException
    {
        try {
            return getController().getStoreSubjectClassCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}