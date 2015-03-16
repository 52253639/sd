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

public class FDCCostLogProductEntry extends CoreBase implements IFDCCostLogProductEntry
{
    public FDCCostLogProductEntry()
    {
        super();
        registerInterface(IFDCCostLogProductEntry.class, this);
    }
    public FDCCostLogProductEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCCostLogProductEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("22FA210C");
    }
    private FDCCostLogProductEntryController getController() throws BOSException
    {
        return (FDCCostLogProductEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public FDCCostLogProductEntryInfo getFDCCostLogProductEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCCostLogProductEntryInfo(getContext(), pk);
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
    public FDCCostLogProductEntryInfo getFDCCostLogProductEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCCostLogProductEntryInfo(getContext(), pk, selector);
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
    public FDCCostLogProductEntryInfo getFDCCostLogProductEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCCostLogProductEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public FDCCostLogProductEntryCollection getFDCCostLogProductEntryCollection() throws BOSException
    {
        try {
            return getController().getFDCCostLogProductEntryCollection(getContext());
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
    public FDCCostLogProductEntryCollection getFDCCostLogProductEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCCostLogProductEntryCollection(getContext(), view);
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
    public FDCCostLogProductEntryCollection getFDCCostLogProductEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCCostLogProductEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}