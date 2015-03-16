package com.kingdee.eas.fdc.sellhouse;

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
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class ShareProperty extends DataBase implements IShareProperty
{
    public ShareProperty()
    {
        super();
        registerInterface(IShareProperty.class, this);
    }
    public ShareProperty(Context ctx)
    {
        super(ctx);
        registerInterface(IShareProperty.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("911F17D9");
    }
    private SharePropertyController getController() throws BOSException
    {
        return (SharePropertyController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public SharePropertyInfo getSharePropertyInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSharePropertyInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public SharePropertyInfo getSharePropertyInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSharePropertyInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public SharePropertyInfo getSharePropertyInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSharePropertyInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public SharePropertyCollection getSharePropertyCollection() throws BOSException
    {
        try {
            return getController().getSharePropertyCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param view view
     *@return
     */
    public SharePropertyCollection getSharePropertyCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSharePropertyCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param oql oql
     *@return
     */
    public SharePropertyCollection getSharePropertyCollection(String oql) throws BOSException
    {
        try {
            return getController().getSharePropertyCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}