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

public class TaskEvaluationBill extends FDCBill implements ITaskEvaluationBill
{
    public TaskEvaluationBill()
    {
        super();
        registerInterface(ITaskEvaluationBill.class, this);
    }
    public TaskEvaluationBill(Context ctx)
    {
        super(ctx);
        registerInterface(ITaskEvaluationBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("7629389E");
    }
    private TaskEvaluationBillController getController() throws BOSException
    {
        return (TaskEvaluationBillController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public TaskEvaluationBillInfo getTaskEvaluationBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTaskEvaluationBillInfo(getContext(), pk);
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
    public TaskEvaluationBillInfo getTaskEvaluationBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTaskEvaluationBillInfo(getContext(), pk, selector);
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
    public TaskEvaluationBillInfo getTaskEvaluationBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTaskEvaluationBillInfo(getContext(), oql);
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
    public TaskEvaluationBillCollection getTaskEvaluationBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getTaskEvaluationBillCollection(getContext(), oql);
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
    public TaskEvaluationBillCollection getTaskEvaluationBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTaskEvaluationBillCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public TaskEvaluationBillCollection getTaskEvaluationBillCollection() throws BOSException
    {
        try {
            return getController().getTaskEvaluationBillCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}