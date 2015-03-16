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
import com.kingdee.eas.framework.IBillBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.BillBase;
import com.kingdee.eas.fdc.market.app.*;

public class MarketManage extends BillBase implements IMarketManage
{
    public MarketManage()
    {
        super();
        registerInterface(IMarketManage.class, this);
    }
    public MarketManage(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketManage.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("5F2D3512");
    }
    private MarketManageController getController() throws BOSException
    {
        return (MarketManageController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public MarketManageInfo getMarketManageInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketManageInfo(getContext(), pk);
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
    public MarketManageInfo getMarketManageInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketManageInfo(getContext(), pk, selector);
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
    public MarketManageInfo getMarketManageInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketManageInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public MarketManageCollection getMarketManageCollection() throws BOSException
    {
        try {
            return getController().getMarketManageCollection(getContext());
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
    public MarketManageCollection getMarketManageCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketManageCollection(getContext(), view);
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
    public MarketManageCollection getMarketManageCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketManageCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}