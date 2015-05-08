package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
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

public class WorkAmountBill extends FDCBill implements IWorkAmountBill
{
    public WorkAmountBill()
    {
        super();
        registerInterface(IWorkAmountBill.class, this);
    }
    public WorkAmountBill(Context ctx)
    {
        super(ctx);
        registerInterface(IWorkAmountBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("75D27BC6");
    }
    private WorkAmountBillController getController() throws BOSException
    {
        return (WorkAmountBillController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public WorkAmountBillInfo getWorkAmountBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getWorkAmountBillInfo(getContext(), pk);
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
    public WorkAmountBillInfo getWorkAmountBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getWorkAmountBillInfo(getContext(), pk, selector);
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
    public WorkAmountBillInfo getWorkAmountBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getWorkAmountBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public WorkAmountBillCollection getWorkAmountBillCollection() throws BOSException
    {
        try {
            return getController().getWorkAmountBillCollection(getContext());
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
    public WorkAmountBillCollection getWorkAmountBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getWorkAmountBillCollection(getContext(), oql);
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
    public WorkAmountBillCollection getWorkAmountBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getWorkAmountBillCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取所工程量信息-User defined method
     *@param projectId 工程项目
     *@return
     */
    public Map initTaskInfo(String projectId) throws BOSException
    {
        try {
            return getController().initTaskInfo(getContext(), projectId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取时间段内的任务-User defined method
     *@param param param
     *@return
     */
    public Map getSelectedTask(Map param) throws BOSException
    {
        try {
            return getController().getSelectedTask(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}