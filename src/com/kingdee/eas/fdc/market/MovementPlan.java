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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.market.app.*;

public class MovementPlan extends FDCBill implements IMovementPlan
{
    public MovementPlan()
    {
        super();
        registerInterface(IMovementPlan.class, this);
    }
    public MovementPlan(Context ctx)
    {
        super(ctx);
        registerInterface(IMovementPlan.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3A1444C9");
    }
    private MovementPlanController getController() throws BOSException
    {
        return (MovementPlanController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public MovementPlanInfo getMovementPlanInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMovementPlanInfo(getContext(), pk);
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
    public MovementPlanInfo getMovementPlanInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMovementPlanInfo(getContext(), pk, selector);
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
    public MovementPlanInfo getMovementPlanInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMovementPlanInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public MovementPlanCollection getMovementPlanCollection() throws BOSException
    {
        try {
            return getController().getMovementPlanCollection(getContext());
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
    public MovementPlanCollection getMovementPlanCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMovementPlanCollection(getContext(), view);
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
    public MovementPlanCollection getMovementPlanCollection(String oql) throws BOSException
    {
        try {
            return getController().getMovementPlanCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}