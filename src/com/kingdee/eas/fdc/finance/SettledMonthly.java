package com.kingdee.eas.fdc.finance;

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
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.Result;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public class SettledMonthly extends CoreBase implements ISettledMonthly
{
    public SettledMonthly()
    {
        super();
        registerInterface(ISettledMonthly.class, this);
    }
    public SettledMonthly(Context ctx)
    {
        super(ctx);
        registerInterface(ISettledMonthly.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C78A4423");
    }
    private SettledMonthlyController getController() throws BOSException
    {
        return (SettledMonthlyController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public SettledMonthlyInfo getSettledMonthlyInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSettledMonthlyInfo(getContext(), pk);
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
    public SettledMonthlyInfo getSettledMonthlyInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSettledMonthlyInfo(getContext(), pk, selector);
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
    public SettledMonthlyInfo getSettledMonthlyInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSettledMonthlyInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public SettledMonthlyCollection getSettledMonthlyCollection() throws BOSException
    {
        try {
            return getController().getSettledMonthlyCollection(getContext());
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
    public SettledMonthlyCollection getSettledMonthlyCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSettledMonthlyCollection(getContext(), view);
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
    public SettledMonthlyCollection getSettledMonthlyCollection(String oql) throws BOSException
    {
        try {
            return getController().getSettledMonthlyCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *集合新增-User defined method
     *@param colls colls
     *@return
     */
    public Result addnew(CoreBaseCollection colls) throws BOSException, EASBizException
    {
        try {
            return getController().addnew(getContext(), colls);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}