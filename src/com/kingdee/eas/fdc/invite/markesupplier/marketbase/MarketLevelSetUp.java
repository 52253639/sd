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

public class MarketLevelSetUp extends FDCDataBase implements IMarketLevelSetUp
{
    public MarketLevelSetUp()
    {
        super();
        registerInterface(IMarketLevelSetUp.class, this);
    }
    public MarketLevelSetUp(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketLevelSetUp.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("508EA982");
    }
    private MarketLevelSetUpController getController() throws BOSException
    {
        return (MarketLevelSetUpController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public MarketLevelSetUpInfo getMarketLevelSetUpInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketLevelSetUpInfo(getContext(), pk);
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
    public MarketLevelSetUpInfo getMarketLevelSetUpInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketLevelSetUpInfo(getContext(), pk, selector);
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
    public MarketLevelSetUpInfo getMarketLevelSetUpInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketLevelSetUpInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public MarketLevelSetUpCollection getMarketLevelSetUpCollection() throws BOSException
    {
        try {
            return getController().getMarketLevelSetUpCollection(getContext());
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
    public MarketLevelSetUpCollection getMarketLevelSetUpCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketLevelSetUpCollection(getContext(), view);
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
    public MarketLevelSetUpCollection getMarketLevelSetUpCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketLevelSetUpCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}