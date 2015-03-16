package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.aimcost.app.*;
import com.kingdee.eas.framework.IBillBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.BillBase;

public class DynamicCost extends BillBase implements IDynamicCost
{
    public DynamicCost()
    {
        super();
        registerInterface(IDynamicCost.class, this);
    }
    public DynamicCost(Context ctx)
    {
        super(ctx);
        registerInterface(IDynamicCost.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F5C696F5");
    }
    private DynamicCostController getController() throws BOSException
    {
        return (DynamicCostController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public DynamicCostInfo getDynamicCostInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDynamicCostInfo(getContext(), pk);
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
    public DynamicCostInfo getDynamicCostInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDynamicCostInfo(getContext(), pk, selector);
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
    public DynamicCostInfo getDynamicCostInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDynamicCostInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public DynamicCostCollection getDynamicCostCollection() throws BOSException
    {
        try {
            return getController().getDynamicCostCollection(getContext());
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
    public DynamicCostCollection getDynamicCostCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDynamicCostCollection(getContext(), view);
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
    public DynamicCostCollection getDynamicCostCollection(String oql) throws BOSException
    {
        try {
            return getController().getDynamicCostCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}