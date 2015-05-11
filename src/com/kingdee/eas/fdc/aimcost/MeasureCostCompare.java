package com.kingdee.eas.fdc.aimcost;

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
import com.kingdee.eas.fdc.aimcost.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class MeasureCostCompare extends CoreBillEntryBase implements IMeasureCostCompare
{
    public MeasureCostCompare()
    {
        super();
        registerInterface(IMeasureCostCompare.class, this);
    }
    public MeasureCostCompare(Context ctx)
    {
        super(ctx);
        registerInterface(IMeasureCostCompare.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("0FF211B1");
    }
    private MeasureCostCompareController getController() throws BOSException
    {
        return (MeasureCostCompareController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public MeasureCostCompareInfo getMeasureCostCompareInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMeasureCostCompareInfo(getContext(), pk);
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
    public MeasureCostCompareInfo getMeasureCostCompareInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMeasureCostCompareInfo(getContext(), pk, selector);
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
    public MeasureCostCompareInfo getMeasureCostCompareInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMeasureCostCompareInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public MeasureCostCompareCollection getMeasureCostCompareCollection() throws BOSException
    {
        try {
            return getController().getMeasureCostCompareCollection(getContext());
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
    public MeasureCostCompareCollection getMeasureCostCompareCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMeasureCostCompareCollection(getContext(), view);
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
    public MeasureCostCompareCollection getMeasureCostCompareCollection(String oql) throws BOSException
    {
        try {
            return getController().getMeasureCostCompareCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}