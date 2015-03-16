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

public class MovementPlanEntry extends BillEntryBase implements IMovementPlanEntry
{
    public MovementPlanEntry()
    {
        super();
        registerInterface(IMovementPlanEntry.class, this);
    }
    public MovementPlanEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IMovementPlanEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("56F46929");
    }
    private MovementPlanEntryController getController() throws BOSException
    {
        return (MovementPlanEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public MovementPlanEntryInfo getMovementPlanEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMovementPlanEntryInfo(getContext(), pk);
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
    public MovementPlanEntryInfo getMovementPlanEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMovementPlanEntryInfo(getContext(), pk, selector);
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
    public MovementPlanEntryInfo getMovementPlanEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMovementPlanEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public MovementPlanEntryCollection getMovementPlanEntryCollection() throws BOSException
    {
        try {
            return getController().getMovementPlanEntryCollection(getContext());
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
    public MovementPlanEntryCollection getMovementPlanEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMovementPlanEntryCollection(getContext(), view);
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
    public MovementPlanEntryCollection getMovementPlanEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getMovementPlanEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}