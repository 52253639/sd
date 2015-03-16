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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.BillEntryBase;
import com.kingdee.eas.framework.IBillEntryBase;

public class IntendingCostEntry extends BillEntryBase implements IIntendingCostEntry
{
    public IntendingCostEntry()
    {
        super();
        registerInterface(IIntendingCostEntry.class, this);
    }
    public IntendingCostEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IIntendingCostEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("614C142D");
    }
    private IntendingCostEntryController getController() throws BOSException
    {
        return (IntendingCostEntryController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public IntendingCostEntryCollection getIntendingCostEntryCollection() throws BOSException
    {
        try {
            return getController().getIntendingCostEntryCollection(getContext());
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
    public IntendingCostEntryCollection getIntendingCostEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getIntendingCostEntryCollection(getContext(), view);
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
    public IntendingCostEntryCollection getIntendingCostEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getIntendingCostEntryCollection(getContext(), oql);
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
    public IntendingCostEntryInfo getIntendingCostEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getIntendingCostEntryInfo(getContext(), pk);
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
    public IntendingCostEntryInfo getIntendingCostEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getIntendingCostEntryInfo(getContext(), pk, selector);
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
    public IntendingCostEntryInfo getIntendingCostEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getIntendingCostEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}