package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.math.BigDecimal;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;

public class TaskEvaluation extends FDCDataBase implements ITaskEvaluation
{
    public TaskEvaluation()
    {
        super();
        registerInterface(ITaskEvaluation.class, this);
    }
    public TaskEvaluation(Context ctx)
    {
        super(ctx);
        registerInterface(ITaskEvaluation.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("54E273F7");
    }
    private TaskEvaluationController getController() throws BOSException
    {
        return (TaskEvaluationController)getBizController();
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
    public TaskEvaluationInfo getTaskEvaluationInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTaskEvaluationInfo(getContext(), pk);
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
    public TaskEvaluationInfo getTaskEvaluationInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTaskEvaluationInfo(getContext(), pk, selector);
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
    public TaskEvaluationInfo getTaskEvaluationInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTaskEvaluationInfo(getContext(), oql);
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
    public TaskEvaluationCollection getTaskEvaluationCollection(String oql) throws BOSException
    {
        try {
            return getController().getTaskEvaluationCollection(getContext(), oql);
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
    public TaskEvaluationCollection getTaskEvaluationCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTaskEvaluationCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public TaskEvaluationCollection getTaskEvaluationCollection() throws BOSException
    {
        try {
            return getController().getTaskEvaluationCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getDataBase-User defined method
     *@param type type
     *@return
     */
    public Set getDataBase(String type) throws BOSException, EASBizException
    {
        try {
            return getController().getDataBase(getContext(), type);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *引用删除-User defined method
     *@param taskId taskId
     *@return
     */
    public boolean quoteDelete(String taskId) throws BOSException, EASBizException
    {
        try {
            return getController().quoteDelete(getContext(), taskId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *修改任务进度-User defined method
     *@param srcId srcId
     *@param complete complete
     *@param isComplete isComplete
     */
    public void updateSchedule(String srcId, BigDecimal complete, boolean isComplete) throws BOSException, EASBizException
    {
        try {
            getController().updateSchedule(getContext(), srcId, complete, isComplete);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}