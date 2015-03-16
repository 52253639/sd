package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.app.*;
import java.util.Date;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ObjectBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IObjectBase;

public class SaleBalance extends ObjectBase implements ISaleBalance
{
    public SaleBalance()
    {
        super();
        registerInterface(ISaleBalance.class, this);
    }
    public SaleBalance(Context ctx)
    {
        super(ctx);
        registerInterface(ISaleBalance.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("85E60ADA");
    }
    private SaleBalanceController getController() throws BOSException
    {
        return (SaleBalanceController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public SaleBalanceInfo getSaleBalanceInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSaleBalanceInfo(getContext(), pk);
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
    public SaleBalanceInfo getSaleBalanceInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSaleBalanceInfo(getContext(), pk, selector);
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
    public SaleBalanceInfo getSaleBalanceInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSaleBalanceInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SaleBalanceCollection getSaleBalanceCollection() throws BOSException
    {
        try {
            return getController().getSaleBalanceCollection(getContext());
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
    public SaleBalanceCollection getSaleBalanceCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSaleBalanceCollection(getContext(), view);
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
    public SaleBalanceCollection getSaleBalanceCollection(String oql) throws BOSException
    {
        try {
            return getController().getSaleBalanceCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *根据销售项目获取最近的结算截止日期-User defined method
     *@param sellProjectId 销售项目编号
     *@return
     */
    public Date getLeatestEndDate(String sellProjectId) throws BOSException
    {
        try {
            return getController().getLeatestEndDate(getContext(), sellProjectId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}