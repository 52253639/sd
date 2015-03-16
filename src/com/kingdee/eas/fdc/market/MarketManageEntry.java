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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.BillEntryBase;
import com.kingdee.eas.framework.IBillEntryBase;
import com.kingdee.eas.fdc.market.app.*;

public class MarketManageEntry extends BillEntryBase implements IMarketManageEntry
{
    public MarketManageEntry()
    {
        super();
        registerInterface(IMarketManageEntry.class, this);
    }
    public MarketManageEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketManageEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("A8833E80");
    }
    private MarketManageEntryController getController() throws BOSException
    {
        return (MarketManageEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public MarketManageEntryInfo getMarketManageEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketManageEntryInfo(getContext(), pk);
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
    public MarketManageEntryInfo getMarketManageEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketManageEntryInfo(getContext(), pk, selector);
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
    public MarketManageEntryInfo getMarketManageEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketManageEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public MarketManageEntryCollection getMarketManageEntryCollection() throws BOSException
    {
        try {
            return getController().getMarketManageEntryCollection(getContext());
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
    public MarketManageEntryCollection getMarketManageEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketManageEntryCollection(getContext(), view);
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
    public MarketManageEntryCollection getMarketManageEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketManageEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}