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

public class SelfAndFinalEvaluation extends FDCBill implements ISelfAndFinalEvaluation
{
    public SelfAndFinalEvaluation()
    {
        super();
        registerInterface(ISelfAndFinalEvaluation.class, this);
    }
    public SelfAndFinalEvaluation(Context ctx)
    {
        super(ctx);
        registerInterface(ISelfAndFinalEvaluation.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E2F0163D");
    }
    private SelfAndFinalEvaluationController getController() throws BOSException
    {
        return (SelfAndFinalEvaluationController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public SelfAndFinalEvaluationInfo getSelfAndFinalEvaluationInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSelfAndFinalEvaluationInfo(getContext(), pk);
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
    public SelfAndFinalEvaluationInfo getSelfAndFinalEvaluationInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSelfAndFinalEvaluationInfo(getContext(), pk, selector);
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
    public SelfAndFinalEvaluationInfo getSelfAndFinalEvaluationInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSelfAndFinalEvaluationInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public SelfAndFinalEvaluationCollection getSelfAndFinalEvaluationCollection() throws BOSException
    {
        try {
            return getController().getSelfAndFinalEvaluationCollection(getContext());
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
    public SelfAndFinalEvaluationCollection getSelfAndFinalEvaluationCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSelfAndFinalEvaluationCollection(getContext(), view);
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
    public SelfAndFinalEvaluationCollection getSelfAndFinalEvaluationCollection(String oql) throws BOSException
    {
        try {
            return getController().getSelfAndFinalEvaluationCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}