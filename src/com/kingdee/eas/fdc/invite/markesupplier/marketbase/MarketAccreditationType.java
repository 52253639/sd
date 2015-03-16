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

public class MarketAccreditationType extends DataBase implements IMarketAccreditationType
{
    public MarketAccreditationType()
    {
        super();
        registerInterface(IMarketAccreditationType.class, this);
    }
    public MarketAccreditationType(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketAccreditationType.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E6C5EC6B");
    }
    private MarketAccreditationTypeController getController() throws BOSException
    {
        return (MarketAccreditationTypeController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public MarketAccreditationTypeInfo getMarketAccreditationTypeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketAccreditationTypeInfo(getContext(), pk);
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
    public MarketAccreditationTypeInfo getMarketAccreditationTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketAccreditationTypeInfo(getContext(), pk, selector);
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
    public MarketAccreditationTypeInfo getMarketAccreditationTypeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketAccreditationTypeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public MarketAccreditationTypeCollection getMarketAccreditationTypeCollection() throws BOSException
    {
        try {
            return getController().getMarketAccreditationTypeCollection(getContext());
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
    public MarketAccreditationTypeCollection getMarketAccreditationTypeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketAccreditationTypeCollection(getContext(), view);
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
    public MarketAccreditationTypeCollection getMarketAccreditationTypeCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketAccreditationTypeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}