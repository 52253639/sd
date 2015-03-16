package com.kingdee.eas.fdc.market;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.market.app.*;

public class SellSupplyPlanEntry extends CoreBillEntryBase implements ISellSupplyPlanEntry
{
    public SellSupplyPlanEntry()
    {
        super();
        registerInterface(ISellSupplyPlanEntry.class, this);
    }
    public SellSupplyPlanEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ISellSupplyPlanEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("1EFECB77");
    }
    private SellSupplyPlanEntryController getController() throws BOSException
    {
        return (SellSupplyPlanEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public SellSupplyPlanEntryInfo getSellSupplyPlanEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSellSupplyPlanEntryInfo(getContext(), pk);
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
    public SellSupplyPlanEntryInfo getSellSupplyPlanEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSellSupplyPlanEntryInfo(getContext(), pk, selector);
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
    public SellSupplyPlanEntryInfo getSellSupplyPlanEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSellSupplyPlanEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SellSupplyPlanEntryCollection getSellSupplyPlanEntryCollection() throws BOSException
    {
        try {
            return getController().getSellSupplyPlanEntryCollection(getContext());
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
    public SellSupplyPlanEntryCollection getSellSupplyPlanEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSellSupplyPlanEntryCollection(getContext(), view);
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
    public SellSupplyPlanEntryCollection getSellSupplyPlanEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getSellSupplyPlanEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}