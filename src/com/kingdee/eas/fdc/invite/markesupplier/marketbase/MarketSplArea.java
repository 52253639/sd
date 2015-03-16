package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;

public class MarketSplArea extends FDCDataBase implements IMarketSplArea
{
    public MarketSplArea()
    {
        super();
        registerInterface(IMarketSplArea.class, this);
    }
    public MarketSplArea(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketSplArea.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F48FCB73");
    }
    private MarketSplAreaController getController() throws BOSException
    {
        return (MarketSplAreaController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public MarketSplAreaInfo getMarketSplAreaInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSplAreaInfo(getContext(), oql);
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
    public MarketSplAreaInfo getMarketSplAreaInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSplAreaInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public MarketSplAreaInfo getMarketSplAreaInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSplAreaInfo(getContext(), pk);
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
    public MarketSplAreaCollection getMarketSplAreaCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketSplAreaCollection(getContext(), oql);
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
    public MarketSplAreaCollection getMarketSplAreaCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketSplAreaCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public MarketSplAreaCollection getMarketSplAreaCollection() throws BOSException
    {
        try {
            return getController().getMarketSplAreaCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *是否删除区域名称-User defined method
     *@param areaName 区域名称
     */
    public void IsNdelete(String areaName) throws BOSException, EASBizException
    {
        try {
            getController().IsNdelete(getContext(), areaName);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}