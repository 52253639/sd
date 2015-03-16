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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class AimCostAdjust extends FDCBill implements IAimCostAdjust
{
    public AimCostAdjust()
    {
        super();
        registerInterface(IAimCostAdjust.class, this);
    }
    public AimCostAdjust(Context ctx)
    {
        super(ctx);
        registerInterface(IAimCostAdjust.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("D8EF24EA");
    }
    private AimCostAdjustController getController() throws BOSException
    {
        return (AimCostAdjustController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public AimCostAdjustInfo getAimCostAdjustInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getAimCostAdjustInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public AimCostAdjustInfo getAimCostAdjustInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getAimCostAdjustInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public AimCostAdjustInfo getAimCostAdjustInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getAimCostAdjustInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public AimCostAdjustCollection getAimCostAdjustCollection() throws BOSException
    {
        try {
            return getController().getAimCostAdjustCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param view view
     *@return
     */
    public AimCostAdjustCollection getAimCostAdjustCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getAimCostAdjustCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param oql oql
     *@return
     */
    public AimCostAdjustCollection getAimCostAdjustCollection(String oql) throws BOSException
    {
        try {
            return getController().getAimCostAdjustCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}