package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class DeptMonthlyScheduleTask extends CoreBillEntryBase implements IDeptMonthlyScheduleTask
{
    public DeptMonthlyScheduleTask()
    {
        super();
        registerInterface(IDeptMonthlyScheduleTask.class, this);
    }
    public DeptMonthlyScheduleTask(Context ctx)
    {
        super(ctx);
        registerInterface(IDeptMonthlyScheduleTask.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("CFE1064E");
    }
    private DeptMonthlyScheduleTaskController getController() throws BOSException
    {
        return (DeptMonthlyScheduleTaskController)getBizController();
    }
    /**
     *exists-System defined method
     *@param pk pk
     *@return
     */
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().exists(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *exists-System defined method
     *@param filter filter
     *@return
     */
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException
    {
        try {
            return getController().exists(getContext(), filter);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *exists-System defined method
     *@param oql oql
     *@return
     */
    public boolean exists(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().exists(getContext(), oql);
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
    public DeptMonthlyScheduleTaskInfo getDeptMonthlyScheduleTaskInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDeptMonthlyScheduleTaskInfo(getContext(), pk);
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
    public DeptMonthlyScheduleTaskInfo getDeptMonthlyScheduleTaskInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDeptMonthlyScheduleTaskInfo(getContext(), pk, selector);
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
    public DeptMonthlyScheduleTaskInfo getDeptMonthlyScheduleTaskInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDeptMonthlyScheduleTaskInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public DeptMonthlyScheduleTaskCollection getDeptMonthlyScheduleTaskCollection() throws BOSException
    {
        try {
            return getController().getDeptMonthlyScheduleTaskCollection(getContext());
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
    public DeptMonthlyScheduleTaskCollection getDeptMonthlyScheduleTaskCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDeptMonthlyScheduleTaskCollection(getContext(), view);
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
    public DeptMonthlyScheduleTaskCollection getDeptMonthlyScheduleTaskCollection(String oql) throws BOSException
    {
        try {
            return getController().getDeptMonthlyScheduleTaskCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}