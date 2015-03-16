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

public class QuestionPaperDefine extends BillBase implements IQuestionPaperDefine
{
    public QuestionPaperDefine()
    {
        super();
        registerInterface(IQuestionPaperDefine.class, this);
    }
    public QuestionPaperDefine(Context ctx)
    {
        super(ctx);
        registerInterface(IQuestionPaperDefine.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("82FA1D30");
    }
    private QuestionPaperDefineController getController() throws BOSException
    {
        return (QuestionPaperDefineController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public QuestionPaperDefineInfo getQuestionPaperDefineInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getQuestionPaperDefineInfo(getContext(), pk);
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
    public QuestionPaperDefineInfo getQuestionPaperDefineInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getQuestionPaperDefineInfo(getContext(), pk, selector);
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
    public QuestionPaperDefineInfo getQuestionPaperDefineInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getQuestionPaperDefineInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public QuestionPaperDefineCollection getQuestionPaperDefineCollection() throws BOSException
    {
        try {
            return getController().getQuestionPaperDefineCollection(getContext());
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
    public QuestionPaperDefineCollection getQuestionPaperDefineCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getQuestionPaperDefineCollection(getContext(), view);
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
    public QuestionPaperDefineCollection getQuestionPaperDefineCollection(String oql) throws BOSException
    {
        try {
            return getController().getQuestionPaperDefineCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *增加新题目-User defined method
     *@param model model
     */
    public void action_NewSubject(QuestionPaperDefineInfo model) throws BOSException
    {
        try {
            getController().action_NewSubject(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *删除题目-User defined method
     *@param model model
     */
    public void action_DeleteSubject(QuestionPaperDefineInfo model) throws BOSException
    {
        try {
            getController().action_DeleteSubject(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *题库选题-User defined method
     *@param model model
     */
    public void action_SelectSubject(QuestionPaperDefineInfo model) throws BOSException
    {
        try {
            getController().action_SelectSubject(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *更改题目-User defined method
     *@param model model
     */
    public void action_UpdateSubject(QuestionPaperDefineInfo model) throws BOSException
    {
        try {
            getController().action_UpdateSubject(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}