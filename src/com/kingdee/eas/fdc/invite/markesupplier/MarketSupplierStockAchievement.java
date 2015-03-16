package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.invite.markesupplier.app.*;

public class MarketSupplierStockAchievement extends CoreBillEntryBase implements IMarketSupplierStockAchievement
{
    public MarketSupplierStockAchievement()
    {
        super();
        registerInterface(IMarketSupplierStockAchievement.class, this);
    }
    public MarketSupplierStockAchievement(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketSupplierStockAchievement.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E5C3B98F");
    }
    private MarketSupplierStockAchievementController getController() throws BOSException
    {
        return (MarketSupplierStockAchievementController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public MarketSupplierStockAchievementInfo getMarketSupplierStockAchievementInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierStockAchievementInfo(getContext(), pk);
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
    public MarketSupplierStockAchievementInfo getMarketSupplierStockAchievementInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierStockAchievementInfo(getContext(), pk, selector);
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
    public MarketSupplierStockAchievementInfo getMarketSupplierStockAchievementInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierStockAchievementInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public MarketSupplierStockAchievementCollection getMarketSupplierStockAchievementCollection() throws BOSException
    {
        try {
            return getController().getMarketSupplierStockAchievementCollection(getContext());
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
    public MarketSupplierStockAchievementCollection getMarketSupplierStockAchievementCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketSupplierStockAchievementCollection(getContext(), view);
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
    public MarketSupplierStockAchievementCollection getMarketSupplierStockAchievementCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketSupplierStockAchievementCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}