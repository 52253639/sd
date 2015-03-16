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

public class FillBlankQuestion extends QuestionBase implements IFillBlankQuestion
{
    public FillBlankQuestion()
    {
        super();
        registerInterface(IFillBlankQuestion.class, this);
    }
    public FillBlankQuestion(Context ctx)
    {
        super(ctx);
        registerInterface(IFillBlankQuestion.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("2389FAE6");
    }
    private FillBlankQuestionController getController() throws BOSException
    {
        return (FillBlankQuestionController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public FillBlankQuestionInfo getFillBlankQuestionInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFillBlankQuestionInfo(getContext(), pk);
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
    public FillBlankQuestionInfo getFillBlankQuestionInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFillBlankQuestionInfo(getContext(), pk, selector);
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
    public FillBlankQuestionInfo getFillBlankQuestionInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFillBlankQuestionInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public FillBlankQuestionCollection getFillBlankQuestionCollection() throws BOSException
    {
        try {
            return getController().getFillBlankQuestionCollection(getContext());
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
    public FillBlankQuestionCollection getFillBlankQuestionCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFillBlankQuestionCollection(getContext(), view);
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
    public FillBlankQuestionCollection getFillBlankQuestionCollection(String oql) throws BOSException
    {
        try {
            return getController().getFillBlankQuestionCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}