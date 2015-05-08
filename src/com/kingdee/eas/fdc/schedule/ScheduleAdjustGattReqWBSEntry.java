package com.kingdee.eas.fdc.schedule;

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
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class ScheduleAdjustGattReqWBSEntry extends CoreBillEntryBase implements IScheduleAdjustGattReqWBSEntry
{
    public ScheduleAdjustGattReqWBSEntry()
    {
        super();
        registerInterface(IScheduleAdjustGattReqWBSEntry.class, this);
    }
    public ScheduleAdjustGattReqWBSEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IScheduleAdjustGattReqWBSEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("EE2091D2");
    }
    private ScheduleAdjustGattReqWBSEntryController getController() throws BOSException
    {
        return (ScheduleAdjustGattReqWBSEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public ScheduleAdjustGattReqWBSEntryInfo getScheduleAdjustGattReqWBSEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getScheduleAdjustGattReqWBSEntryInfo(getContext(), oql);
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
    public ScheduleAdjustGattReqWBSEntryInfo getScheduleAdjustGattReqWBSEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getScheduleAdjustGattReqWBSEntryInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ScheduleAdjustGattReqWBSEntryInfo getScheduleAdjustGattReqWBSEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getScheduleAdjustGattReqWBSEntryInfo(getContext(), pk);
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
    public ScheduleAdjustGattReqWBSEntryCollection getScheduleAdjustGattReqWBSEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getScheduleAdjustGattReqWBSEntryCollection(getContext(), oql);
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
    public ScheduleAdjustGattReqWBSEntryCollection getScheduleAdjustGattReqWBSEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getScheduleAdjustGattReqWBSEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ScheduleAdjustGattReqWBSEntryCollection getScheduleAdjustGattReqWBSEntryCollection() throws BOSException
    {
        try {
            return getController().getScheduleAdjustGattReqWBSEntryCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}