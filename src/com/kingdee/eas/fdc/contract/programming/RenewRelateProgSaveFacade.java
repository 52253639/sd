package com.kingdee.eas.fdc.contract.programming;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectCollection;
import java.lang.Object;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.programming.app.*;
import java.util.Set;

public class RenewRelateProgSaveFacade extends AbstractBizCtrl implements IRenewRelateProgSaveFacade
{
    public RenewRelateProgSaveFacade()
    {
        super();
        registerInterface(IRenewRelateProgSaveFacade.class, this);
    }
    public RenewRelateProgSaveFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IRenewRelateProgSaveFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("88151627");
    }
    private RenewRelateProgSaveFacadeController getController() throws BOSException
    {
        return (RenewRelateProgSaveFacadeController)getBizController();
    }
    /**
     *保存框架合约-User defined method
     *@param objCol objCol
     */
    public void save(IObjectCollection objCol) throws BOSException, EASBizException
    {
        try {
            getController().save(getContext(), objCol);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *当param数据超过2000个SQL Server会报错，因此要放入服务端-User defined method
     *@param id id
     *@return
     */
    public Set getContractbillID(Object[] id) throws BOSException
    {
        try {
            return getController().getContractbillID(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}