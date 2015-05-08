package com.kingdee.eas.fdc.schedule;

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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class ScheduleQualityCheckPoint extends FDCBill implements IScheduleQualityCheckPoint
{
    public ScheduleQualityCheckPoint()
    {
        super();
        registerInterface(IScheduleQualityCheckPoint.class, this);
    }
    public ScheduleQualityCheckPoint(Context ctx)
    {
        super(ctx);
        registerInterface(IScheduleQualityCheckPoint.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("25700EDA");
    }
    private ScheduleQualityCheckPointController getController() throws BOSException
    {
        return (ScheduleQualityCheckPointController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ScheduleQualityCheckPointInfo getScheduleQualityCheckPointInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getScheduleQualityCheckPointInfo(getContext(), pk);
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
    public ScheduleQualityCheckPointInfo getScheduleQualityCheckPointInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getScheduleQualityCheckPointInfo(getContext(), pk, selector);
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
    public ScheduleQualityCheckPointInfo getScheduleQualityCheckPointInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getScheduleQualityCheckPointInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ScheduleQualityCheckPointCollection getScheduleQualityCheckPointCollection() throws BOSException
    {
        try {
            return getController().getScheduleQualityCheckPointCollection(getContext());
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
    public ScheduleQualityCheckPointCollection getScheduleQualityCheckPointCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getScheduleQualityCheckPointCollection(getContext(), view);
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
    public ScheduleQualityCheckPointCollection getScheduleQualityCheckPointCollection(String oql) throws BOSException
    {
        try {
            return getController().getScheduleQualityCheckPointCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}