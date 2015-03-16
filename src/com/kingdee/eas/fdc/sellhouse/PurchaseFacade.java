package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.framework.*;
import java.util.Set;

public class PurchaseFacade extends AbstractBizCtrl implements IPurchaseFacade
{
    public PurchaseFacade()
    {
        super();
        registerInterface(IPurchaseFacade.class, this);
    }
    public PurchaseFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IPurchaseFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9796E896");
    }
    private PurchaseFacadeController getController() throws BOSException
    {
        return (PurchaseFacadeController)getBizController();
    }
    /**
     *过滤查询-User defined method
     *@param state 认购状态
     *@param i 收款金额是否大于0
     *@return
     */
    public Set findByChanged(int state, boolean i) throws BOSException
    {
        try {
            return getController().findByChanged(getContext(), state, i);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *查询状态-User defined method
     *@return
     */
    public IRowSet findAllState() throws BOSException
    {
        try {
            return getController().findAllState(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}