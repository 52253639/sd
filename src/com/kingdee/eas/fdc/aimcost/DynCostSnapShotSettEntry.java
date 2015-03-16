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
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public class DynCostSnapShotSettEntry extends CoreBase implements IDynCostSnapShotSettEntry
{
    public DynCostSnapShotSettEntry()
    {
        super();
        registerInterface(IDynCostSnapShotSettEntry.class, this);
    }
    public DynCostSnapShotSettEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IDynCostSnapShotSettEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("7A9818CD");
    }
    private DynCostSnapShotSettEntryController getController() throws BOSException
    {
        return (DynCostSnapShotSettEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public DynCostSnapShotSettEntryInfo getDynCostSnapShotSettEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDynCostSnapShotSettEntryInfo(getContext(), pk);
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
    public DynCostSnapShotSettEntryInfo getDynCostSnapShotSettEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDynCostSnapShotSettEntryInfo(getContext(), pk, selector);
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
    public DynCostSnapShotSettEntryInfo getDynCostSnapShotSettEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDynCostSnapShotSettEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public DynCostSnapShotSettEntryCollection getDynCostSnapShotSettEntryCollection() throws BOSException
    {
        try {
            return getController().getDynCostSnapShotSettEntryCollection(getContext());
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
    public DynCostSnapShotSettEntryCollection getDynCostSnapShotSettEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDynCostSnapShotSettEntryCollection(getContext(), view);
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
    public DynCostSnapShotSettEntryCollection getDynCostSnapShotSettEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getDynCostSnapShotSettEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}