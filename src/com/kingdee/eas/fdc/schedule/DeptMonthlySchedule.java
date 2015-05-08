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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;

public class DeptMonthlySchedule extends FDCBill implements IDeptMonthlySchedule
{
    public DeptMonthlySchedule()
    {
        super();
        registerInterface(IDeptMonthlySchedule.class, this);
    }
    public DeptMonthlySchedule(Context ctx)
    {
        super(ctx);
        registerInterface(IDeptMonthlySchedule.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E32C1929");
    }
    private DeptMonthlyScheduleController getController() throws BOSException
    {
        return (DeptMonthlyScheduleController)getBizController();
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public DeptMonthlyScheduleCollection getDeptMonthlyScheduleCollection() throws BOSException
    {
        try {
            return getController().getDeptMonthlyScheduleCollection(getContext());
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
    public DeptMonthlyScheduleCollection getDeptMonthlyScheduleCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDeptMonthlyScheduleCollection(getContext(), view);
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
    public DeptMonthlyScheduleCollection getDeptMonthlyScheduleCollection(String oql) throws BOSException
    {
        try {
            return getController().getDeptMonthlyScheduleCollection(getContext(), oql);
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
    public DeptMonthlyScheduleInfo getDeptMonthlyScheduleInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDeptMonthlyScheduleInfo(getContext(), pk);
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
    public DeptMonthlyScheduleInfo getDeptMonthlyScheduleInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDeptMonthlyScheduleInfo(getContext(), pk, selector);
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
    public DeptMonthlyScheduleInfo getDeptMonthlyScheduleInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDeptMonthlyScheduleInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
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
     *获得所有的任务-User defined method
     *@param year year
     *@param month month
     *@param id id
     *@return
     */
    public List getAllTask(int year, int month, String id) throws BOSException, EASBizException
    {
        try {
            return getController().getAllTask(getContext(), year, month, id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获得所有的任务-User defined method
     *@param id id
     *@return
     */
    public List getAllTask(String id) throws BOSException, EASBizException
    {
        try {
            return getController().getAllTask(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *提交到工作流-User defined method
     *@param model model
     */
    public void submitForWF(IObjectValue model) throws BOSException, EASBizException
    {
        try {
            getController().submitForWF(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}