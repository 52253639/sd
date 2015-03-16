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

public class FDCCostLogChangeSettEntry extends CoreBase implements IFDCCostLogChangeSettEntry
{
    public FDCCostLogChangeSettEntry()
    {
        super();
        registerInterface(IFDCCostLogChangeSettEntry.class, this);
    }
    public FDCCostLogChangeSettEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCCostLogChangeSettEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("0F0FF487");
    }
    private FDCCostLogChangeSettEntryController getController() throws BOSException
    {
        return (FDCCostLogChangeSettEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public FDCCostLogChangeSettEntryInfo getFDCCostLogChangeSettEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCCostLogChangeSettEntryInfo(getContext(), pk);
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
    public FDCCostLogChangeSettEntryInfo getFDCCostLogChangeSettEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCCostLogChangeSettEntryInfo(getContext(), pk, selector);
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
    public FDCCostLogChangeSettEntryInfo getFDCCostLogChangeSettEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCCostLogChangeSettEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public FDCCostLogChangeSettEntryCollection getFDCCostLogChangeSettEntryCollection() throws BOSException
    {
        try {
            return getController().getFDCCostLogChangeSettEntryCollection(getContext());
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
    public FDCCostLogChangeSettEntryCollection getFDCCostLogChangeSettEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCCostLogChangeSettEntryCollection(getContext(), view);
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
    public FDCCostLogChangeSettEntryCollection getFDCCostLogChangeSettEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCCostLogChangeSettEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}