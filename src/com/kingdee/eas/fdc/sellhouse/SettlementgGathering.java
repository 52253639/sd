package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.eas.framework.IBillBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.BillBase;

public class SettlementgGathering extends BillBase implements ISettlementgGathering
{
    public SettlementgGathering()
    {
        super();
        registerInterface(ISettlementgGathering.class, this);
    }
    public SettlementgGathering(Context ctx)
    {
        super(ctx);
        registerInterface(ISettlementgGathering.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B2E12CA4");
    }
    private SettlementgGatheringController getController() throws BOSException
    {
        return (SettlementgGatheringController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public SettlementgGatheringInfo getSettlementgGatheringInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSettlementgGatheringInfo(getContext(), pk);
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
    public SettlementgGatheringInfo getSettlementgGatheringInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSettlementgGatheringInfo(getContext(), pk, selector);
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
    public SettlementgGatheringInfo getSettlementgGatheringInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSettlementgGatheringInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SettlementgGatheringCollection getSettlementgGatheringCollection() throws BOSException
    {
        try {
            return getController().getSettlementgGatheringCollection(getContext());
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
    public SettlementgGatheringCollection getSettlementgGatheringCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSettlementgGatheringCollection(getContext(), view);
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
    public SettlementgGatheringCollection getSettlementgGatheringCollection(String oql) throws BOSException
    {
        try {
            return getController().getSettlementgGatheringCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}