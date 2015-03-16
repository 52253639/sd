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
import com.kingdee.eas.framework.DataBase;
import com.kingdee.eas.fdc.aimcost.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class AimCostProductSplitEntry extends DataBase implements IAimCostProductSplitEntry
{
    public AimCostProductSplitEntry()
    {
        super();
        registerInterface(IAimCostProductSplitEntry.class, this);
    }
    public AimCostProductSplitEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IAimCostProductSplitEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("59124F28");
    }
    private AimCostProductSplitEntryController getController() throws BOSException
    {
        return (AimCostProductSplitEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public AimCostProductSplitEntryInfo getAimCostProductSplitEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getAimCostProductSplitEntryInfo(getContext(), pk);
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
    public AimCostProductSplitEntryInfo getAimCostProductSplitEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getAimCostProductSplitEntryInfo(getContext(), pk, selector);
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
    public AimCostProductSplitEntryInfo getAimCostProductSplitEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getAimCostProductSplitEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public AimCostProductSplitEntryCollection getAimCostProductSplitEntryCollection() throws BOSException
    {
        try {
            return getController().getAimCostProductSplitEntryCollection(getContext());
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
    public AimCostProductSplitEntryCollection getAimCostProductSplitEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getAimCostProductSplitEntryCollection(getContext(), view);
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
    public AimCostProductSplitEntryCollection getAimCostProductSplitEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getAimCostProductSplitEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}