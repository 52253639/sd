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

public class SelfAndFinalEvaluationEntry extends CoreBillEntryBase implements ISelfAndFinalEvaluationEntry
{
    public SelfAndFinalEvaluationEntry()
    {
        super();
        registerInterface(ISelfAndFinalEvaluationEntry.class, this);
    }
    public SelfAndFinalEvaluationEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ISelfAndFinalEvaluationEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("69C56035");
    }
    private SelfAndFinalEvaluationEntryController getController() throws BOSException
    {
        return (SelfAndFinalEvaluationEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public SelfAndFinalEvaluationEntryInfo getSelfAndFinalEvaluationEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSelfAndFinalEvaluationEntryInfo(getContext(), pk);
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
    public SelfAndFinalEvaluationEntryInfo getSelfAndFinalEvaluationEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSelfAndFinalEvaluationEntryInfo(getContext(), pk, selector);
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
    public SelfAndFinalEvaluationEntryInfo getSelfAndFinalEvaluationEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSelfAndFinalEvaluationEntryInfo(getContext(), oql);
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
    public SelfAndFinalEvaluationEntryCollection getSelfAndFinalEvaluationEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getSelfAndFinalEvaluationEntryCollection(getContext(), oql);
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
    public SelfAndFinalEvaluationEntryCollection getSelfAndFinalEvaluationEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSelfAndFinalEvaluationEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public SelfAndFinalEvaluationEntryCollection getSelfAndFinalEvaluationEntryCollection() throws BOSException
    {
        try {
            return getController().getSelfAndFinalEvaluationEntryCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}