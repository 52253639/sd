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

public class JudgeQuestion extends QuestionBase implements IJudgeQuestion
{
    public JudgeQuestion()
    {
        super();
        registerInterface(IJudgeQuestion.class, this);
    }
    public JudgeQuestion(Context ctx)
    {
        super(ctx);
        registerInterface(IJudgeQuestion.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("767C5DEC");
    }
    private JudgeQuestionController getController() throws BOSException
    {
        return (JudgeQuestionController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public JudgeQuestionInfo getJudgeQuestionInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getJudgeQuestionInfo(getContext(), pk);
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
    public JudgeQuestionInfo getJudgeQuestionInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getJudgeQuestionInfo(getContext(), pk, selector);
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
    public JudgeQuestionInfo getJudgeQuestionInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getJudgeQuestionInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public JudgeQuestionCollection getJudgeQuestionCollection() throws BOSException
    {
        try {
            return getController().getJudgeQuestionCollection(getContext());
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
    public JudgeQuestionCollection getJudgeQuestionCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getJudgeQuestionCollection(getContext(), view);
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
    public JudgeQuestionCollection getJudgeQuestionCollection(String oql) throws BOSException
    {
        try {
            return getController().getJudgeQuestionCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}