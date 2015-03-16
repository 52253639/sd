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
import com.kingdee.eas.framework.DataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.market.app.*;
import com.kingdee.eas.framework.IDataBase;

public class QuestionBase extends DataBase implements IQuestionBase
{
    public QuestionBase()
    {
        super();
        registerInterface(IQuestionBase.class, this);
    }
    public QuestionBase(Context ctx)
    {
        super(ctx);
        registerInterface(IQuestionBase.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("652F8828");
    }
    private QuestionBaseController getController() throws BOSException
    {
        return (QuestionBaseController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public QuestionBaseInfo getQuestionBaseInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getQuestionBaseInfo(getContext(), pk);
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
    public QuestionBaseInfo getQuestionBaseInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getQuestionBaseInfo(getContext(), pk, selector);
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
    public QuestionBaseInfo getQuestionBaseInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getQuestionBaseInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public QuestionBaseCollection getQuestionBaseCollection() throws BOSException
    {
        try {
            return getController().getQuestionBaseCollection(getContext());
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
    public QuestionBaseCollection getQuestionBaseCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getQuestionBaseCollection(getContext(), view);
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
    public QuestionBaseCollection getQuestionBaseCollection(String oql) throws BOSException
    {
        try {
            return getController().getQuestionBaseCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}