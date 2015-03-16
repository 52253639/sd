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
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.TreeBase;

public class MarketSplAuditIndexTree extends TreeBase implements IMarketSplAuditIndexTree
{
    public MarketSplAuditIndexTree()
    {
        super();
        registerInterface(IMarketSplAuditIndexTree.class, this);
    }
    public MarketSplAuditIndexTree(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketSplAuditIndexTree.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("6DDF155B");
    }
    private MarketSplAuditIndexTreeController getController() throws BOSException
    {
        return (MarketSplAuditIndexTreeController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public MarketSplAuditIndexTreeInfo getMarketSplAuditIndexTreeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSplAuditIndexTreeInfo(getContext(), pk);
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
    public MarketSplAuditIndexTreeInfo getMarketSplAuditIndexTreeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSplAuditIndexTreeInfo(getContext(), pk, selector);
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
    public MarketSplAuditIndexTreeInfo getMarketSplAuditIndexTreeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSplAuditIndexTreeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public MarketSplAuditIndexTreeCollection getMarketSplAuditIndexTreeCollection() throws BOSException
    {
        try {
            return getController().getMarketSplAuditIndexTreeCollection(getContext());
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
    public MarketSplAuditIndexTreeCollection getMarketSplAuditIndexTreeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketSplAuditIndexTreeCollection(getContext(), view);
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
    public MarketSplAuditIndexTreeCollection getMarketSplAuditIndexTreeCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketSplAuditIndexTreeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}