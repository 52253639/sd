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

public class ActionEvaluate extends DataBase implements IActionEvaluate
{
    public ActionEvaluate()
    {
        super();
        registerInterface(IActionEvaluate.class, this);
    }
    public ActionEvaluate(Context ctx)
    {
        super(ctx);
        registerInterface(IActionEvaluate.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("887E5D00");
    }
    private ActionEvaluateController getController() throws BOSException
    {
        return (ActionEvaluateController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ActionEvaluateInfo getActionEvaluateInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getActionEvaluateInfo(getContext(), pk);
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
    public ActionEvaluateInfo getActionEvaluateInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getActionEvaluateInfo(getContext(), pk, selector);
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
    public ActionEvaluateInfo getActionEvaluateInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getActionEvaluateInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ActionEvaluateCollection getActionEvaluateCollection() throws BOSException
    {
        try {
            return getController().getActionEvaluateCollection(getContext());
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
    public ActionEvaluateCollection getActionEvaluateCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getActionEvaluateCollection(getContext(), view);
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
    public ActionEvaluateCollection getActionEvaluateCollection(String oql) throws BOSException
    {
        try {
            return getController().getActionEvaluateCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}