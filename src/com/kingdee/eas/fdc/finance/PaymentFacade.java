package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.finance.app.*;
import java.util.Map;
import com.kingdee.bos.framework.*;

public class PaymentFacade extends AbstractBizCtrl implements IPaymentFacade
{
    public PaymentFacade()
    {
        super();
        registerInterface(IPaymentFacade.class, this);
    }
    public PaymentFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IPaymentFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("18821D91");
    }
    private PaymentFacadeController getController() throws BOSException
    {
        return (PaymentFacadeController)getBizController();
    }
    /**
     *获取初始数据-User defined method
     *@param param param
     *@return
     */
    public Map fetchInitData(Map param) throws BOSException, EASBizException
    {
        try {
            return getController().fetchInitData(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取计划执行请款数据-User defined method
     *@param param param
     *@return
     */
    public Map fetchPayPlanData(Map param) throws BOSException, EASBizException
    {
        try {
            return getController().fetchPayPlanData(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}