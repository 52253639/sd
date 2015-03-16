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

public class ShareSeller extends DataBase implements IShareSeller
{
    public ShareSeller()
    {
        super();
        registerInterface(IShareSeller.class, this);
    }
    public ShareSeller(Context ctx)
    {
        super(ctx);
        registerInterface(IShareSeller.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3E9F3A83");
    }
    private ShareSellerController getController() throws BOSException
    {
        return (ShareSellerController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public ShareSellerInfo getShareSellerInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getShareSellerInfo(getContext(), pk);
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
    public ShareSellerInfo getShareSellerInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getShareSellerInfo(getContext(), pk, selector);
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
    public ShareSellerInfo getShareSellerInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getShareSellerInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ShareSellerCollection getShareSellerCollection() throws BOSException
    {
        try {
            return getController().getShareSellerCollection(getContext());
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
    public ShareSellerCollection getShareSellerCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getShareSellerCollection(getContext(), view);
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
    public ShareSellerCollection getShareSellerCollection(String oql) throws BOSException
    {
        try {
            return getController().getShareSellerCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}