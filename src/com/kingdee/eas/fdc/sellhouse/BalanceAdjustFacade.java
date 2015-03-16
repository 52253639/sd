package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.framework.*;

public class BalanceAdjustFacade extends AbstractBizCtrl implements IBalanceAdjustFacade
{
    public BalanceAdjustFacade()
    {
        super();
        registerInterface(IBalanceAdjustFacade.class, this);
    }
    public BalanceAdjustFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IBalanceAdjustFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("FA5F434A");
    }
    private BalanceAdjustFacadeController getController() throws BOSException
    {
        return (BalanceAdjustFacadeController)getBizController();
    }
    /**
     *作废收款单-User defined method
     *@param id id
     *@param isInv isInv
     *@param isVerify isVerify
     */
    public void blankOutRevBill(String id, boolean isInv, boolean isVerify) throws BOSException, EASBizException
    {
        try {
            getController().blankOutRevBill(getContext(), id, isInv, isVerify);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *作废认购单-User defined method
     *@param id id
     */
    public void blankOutPurchase(String id) throws BOSException, EASBizException
    {
        try {
            getController().blankOutPurchase(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}