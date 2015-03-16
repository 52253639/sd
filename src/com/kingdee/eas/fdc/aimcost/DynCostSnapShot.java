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
import java.util.Map;
import com.kingdee.eas.fdc.aimcost.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ObjectBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IObjectBase;

public class DynCostSnapShot extends ObjectBase implements IDynCostSnapShot
{
    public DynCostSnapShot()
    {
        super();
        registerInterface(IDynCostSnapShot.class, this);
    }
    public DynCostSnapShot(Context ctx)
    {
        super(ctx);
        registerInterface(IDynCostSnapShot.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C2CE2BB3");
    }
    private DynCostSnapShotController getController() throws BOSException
    {
        return (DynCostSnapShotController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public DynCostSnapShotCollection getDynCostSnapShotCollection() throws BOSException
    {
        try {
            return getController().getDynCostSnapShotCollection(getContext());
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
    public DynCostSnapShotCollection getDynCostSnapShotCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDynCostSnapShotCollection(getContext(), view);
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
    public DynCostSnapShotCollection getDynCostSnapShotCollection(String oql) throws BOSException
    {
        try {
            return getController().getDynCostSnapShotCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public DynCostSnapShotInfo getDynCostSnapShotInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDynCostSnapShotInfo(getContext(), pk);
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
    public DynCostSnapShotInfo getDynCostSnapShotInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDynCostSnapShotInfo(getContext(), pk, selector);
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
    public DynCostSnapShotInfo getDynCostSnapShotInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDynCostSnapShotInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *根据月获取历史-User defined method
     *@param selectMonth selectMonth
     *@return
     */
    public Map getHistoryByMonth(Map selectMonth) throws BOSException, EASBizException
    {
        try {
            return getController().getHistoryByMonth(getContext(), selectMonth);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}