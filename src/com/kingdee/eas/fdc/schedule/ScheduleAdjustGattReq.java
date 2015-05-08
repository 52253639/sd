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

public class ScheduleAdjustGattReq extends FDCBill implements IScheduleAdjustGattReq
{
    public ScheduleAdjustGattReq()
    {
        super();
        registerInterface(IScheduleAdjustGattReq.class, this);
    }
    public ScheduleAdjustGattReq(Context ctx)
    {
        super(ctx);
        registerInterface(IScheduleAdjustGattReq.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("6CDE9F68");
    }
    private ScheduleAdjustGattReqController getController() throws BOSException
    {
        return (ScheduleAdjustGattReqController)getBizController();
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ScheduleAdjustGattReqCollection getScheduleAdjustGattReqCollection() throws BOSException
    {
        try {
            return getController().getScheduleAdjustGattReqCollection(getContext());
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
    public ScheduleAdjustGattReqCollection getScheduleAdjustGattReqCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getScheduleAdjustGattReqCollection(getContext(), view);
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
    public ScheduleAdjustGattReqCollection getScheduleAdjustGattReqCollection(String oql) throws BOSException
    {
        try {
            return getController().getScheduleAdjustGattReqCollection(getContext(), oql);
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
    public ScheduleAdjustGattReqInfo getScheduleAdjustGattReqInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getScheduleAdjustGattReqInfo(getContext(), pk);
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
    public ScheduleAdjustGattReqInfo getScheduleAdjustGattReqInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getScheduleAdjustGattReqInfo(getContext(), pk, selector);
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
    public ScheduleAdjustGattReqInfo getScheduleAdjustGattReqInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getScheduleAdjustGattReqInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}