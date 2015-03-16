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
import com.kingdee.eas.framework.DataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.market.app.*;
import com.kingdee.eas.framework.IDataBase;

public class MarketDisplaySet extends DataBase implements IMarketDisplaySet
{
    public MarketDisplaySet()
    {
        super();
        registerInterface(IMarketDisplaySet.class, this);
    }
    public MarketDisplaySet(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketDisplaySet.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("2182556D");
    }
    private MarketDisplaySetController getController() throws BOSException
    {
        return (MarketDisplaySetController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public MarketDisplaySetInfo getMarketDisplaySetInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketDisplaySetInfo(getContext(), pk);
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
    public MarketDisplaySetInfo getMarketDisplaySetInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketDisplaySetInfo(getContext(), pk, selector);
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
    public MarketDisplaySetInfo getMarketDisplaySetInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketDisplaySetInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public MarketDisplaySetCollection getMarketDisplaySetCollection() throws BOSException
    {
        try {
            return getController().getMarketDisplaySetCollection(getContext());
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
    public MarketDisplaySetCollection getMarketDisplaySetCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketDisplaySetCollection(getContext(), view);
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
    public MarketDisplaySetCollection getMarketDisplaySetCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketDisplaySetCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}