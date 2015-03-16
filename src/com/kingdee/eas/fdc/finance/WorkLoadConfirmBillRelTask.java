package com.kingdee.eas.fdc.finance;

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
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class WorkLoadConfirmBillRelTask extends CoreBillEntryBase implements IWorkLoadConfirmBillRelTask
{
    public WorkLoadConfirmBillRelTask()
    {
        super();
        registerInterface(IWorkLoadConfirmBillRelTask.class, this);
    }
    public WorkLoadConfirmBillRelTask(Context ctx)
    {
        super(ctx);
        registerInterface(IWorkLoadConfirmBillRelTask.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("7F6F991D");
    }
    private WorkLoadConfirmBillRelTaskController getController() throws BOSException
    {
        return (WorkLoadConfirmBillRelTaskController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public WorkLoadConfirmBillRelTaskInfo getWorkLoadConfirmBillRelTaskInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getWorkLoadConfirmBillRelTaskInfo(getContext(), pk);
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
    public WorkLoadConfirmBillRelTaskInfo getWorkLoadConfirmBillRelTaskInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getWorkLoadConfirmBillRelTaskInfo(getContext(), pk, selector);
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
    public WorkLoadConfirmBillRelTaskInfo getWorkLoadConfirmBillRelTaskInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getWorkLoadConfirmBillRelTaskInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public WorkLoadConfirmBillRelTaskCollection getWorkLoadConfirmBillRelTaskCollection() throws BOSException
    {
        try {
            return getController().getWorkLoadConfirmBillRelTaskCollection(getContext());
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
    public WorkLoadConfirmBillRelTaskCollection getWorkLoadConfirmBillRelTaskCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getWorkLoadConfirmBillRelTaskCollection(getContext(), view);
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
    public WorkLoadConfirmBillRelTaskCollection getWorkLoadConfirmBillRelTaskCollection(String oql) throws BOSException
    {
        try {
            return getController().getWorkLoadConfirmBillRelTaskCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}