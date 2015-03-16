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

public class MarketSupplierAppraiseTemplateTree extends TreeBase implements IMarketSupplierAppraiseTemplateTree
{
    public MarketSupplierAppraiseTemplateTree()
    {
        super();
        registerInterface(IMarketSupplierAppraiseTemplateTree.class, this);
    }
    public MarketSupplierAppraiseTemplateTree(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketSupplierAppraiseTemplateTree.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("0EF9F8F8");
    }
    private MarketSupplierAppraiseTemplateTreeController getController() throws BOSException
    {
        return (MarketSupplierAppraiseTemplateTreeController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public MarketSupplierAppraiseTemplateTreeInfo getMarketSupplierAppraiseTemplateTreeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierAppraiseTemplateTreeInfo(getContext(), pk);
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
    public MarketSupplierAppraiseTemplateTreeInfo getMarketSupplierAppraiseTemplateTreeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierAppraiseTemplateTreeInfo(getContext(), pk, selector);
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
    public MarketSupplierAppraiseTemplateTreeInfo getMarketSupplierAppraiseTemplateTreeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierAppraiseTemplateTreeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public MarketSupplierAppraiseTemplateTreeCollection getMarketSupplierAppraiseTemplateTreeCollection() throws BOSException
    {
        try {
            return getController().getMarketSupplierAppraiseTemplateTreeCollection(getContext());
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
    public MarketSupplierAppraiseTemplateTreeCollection getMarketSupplierAppraiseTemplateTreeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketSupplierAppraiseTemplateTreeCollection(getContext(), view);
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
    public MarketSupplierAppraiseTemplateTreeCollection getMarketSupplierAppraiseTemplateTreeCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketSupplierAppraiseTemplateTreeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}