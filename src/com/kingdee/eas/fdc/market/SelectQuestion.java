package com.kingdee.eas.fdc.market;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.market.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public class SelectQuestion extends QuestionBase implements ISelectQuestion
{
    public SelectQuestion()
    {
        super();
        registerInterface(ISelectQuestion.class, this);
    }
    public SelectQuestion(Context ctx)
    {
        super(ctx);
        registerInterface(ISelectQuestion.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("6D037D13");
    }
    private SelectQuestionController getController() throws BOSException
    {
        return (SelectQuestionController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public SelectQuestionInfo getSelectQuestionInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSelectQuestionInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public SelectQuestionInfo getSelectQuestionInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSelectQuestionInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql 取值
     *@return
     */
    public SelectQuestionInfo getSelectQuestionInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSelectQuestionInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SelectQuestionCollection getSelectQuestionCollection() throws BOSException
    {
        try {
            return getController().getSelectQuestionCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view 取集合
     *@return
     */
    public SelectQuestionCollection getSelectQuestionCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSelectQuestionCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql 取集合
     *@return
     */
    public SelectQuestionCollection getSelectQuestionCollection(String oql) throws BOSException
    {
        try {
            return getController().getSelectQuestionCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}