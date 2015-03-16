package com.kingdee.eas.fdc.market;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.IBillBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.BillBase;
import com.kingdee.eas.fdc.market.app.*;

public class QuestionPaperAnswer extends BillBase implements IQuestionPaperAnswer
{
    public QuestionPaperAnswer()
    {
        super();
        registerInterface(IQuestionPaperAnswer.class, this);
    }
    public QuestionPaperAnswer(Context ctx)
    {
        super(ctx);
        registerInterface(IQuestionPaperAnswer.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("7E608333");
    }
    private QuestionPaperAnswerController getController() throws BOSException
    {
        return (QuestionPaperAnswerController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public QuestionPaperAnswerInfo getQuestionPaperAnswerInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getQuestionPaperAnswerInfo(getContext(), pk);
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
    public QuestionPaperAnswerInfo getQuestionPaperAnswerInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getQuestionPaperAnswerInfo(getContext(), pk, selector);
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
    public QuestionPaperAnswerInfo getQuestionPaperAnswerInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getQuestionPaperAnswerInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public QuestionPaperAnswerCollection getQuestionPaperAnswerCollection() throws BOSException
    {
        try {
            return getController().getQuestionPaperAnswerCollection(getContext());
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
    public QuestionPaperAnswerCollection getQuestionPaperAnswerCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getQuestionPaperAnswerCollection(getContext(), view);
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
    public QuestionPaperAnswerCollection getQuestionPaperAnswerCollection(String oql) throws BOSException
    {
        try {
            return getController().getQuestionPaperAnswerCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}