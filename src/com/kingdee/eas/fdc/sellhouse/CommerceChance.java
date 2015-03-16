package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class CommerceChance extends FDCBill implements ICommerceChance
{
    public CommerceChance()
    {
        super();
        registerInterface(ICommerceChance.class, this);
    }
    public CommerceChance(Context ctx)
    {
        super(ctx);
        registerInterface(ICommerceChance.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("AB97E58A");
    }
    private CommerceChanceController getController() throws BOSException
    {
        return (CommerceChanceController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public CommerceChanceInfo getCommerceChanceInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCommerceChanceInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public CommerceChanceInfo getCommerceChanceInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCommerceChanceInfo(getContext(), pk);
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
    public CommerceChanceInfo getCommerceChanceInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCommerceChanceInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public CommerceChanceCollection getCommerceChanceCollection() throws BOSException
    {
        try {
            return getController().getCommerceChanceCollection(getContext());
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
    public CommerceChanceCollection getCommerceChanceCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCommerceChanceCollection(getContext(), view);
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
    public CommerceChanceCollection getCommerceChanceCollection(String oql) throws BOSException
    {
        try {
            return getController().getCommerceChanceCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *转交易-User defined method
     *@param type 类型
     *@param id id
     */
    public void updateToTransaction(String type, String id) throws BOSException, EASBizException
    {
        try {
            getController().updateToTransaction(getContext(), type, id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}