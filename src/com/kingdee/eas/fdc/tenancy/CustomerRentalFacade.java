package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.tenancy.app.*;
import java.util.Map;
import com.kingdee.bos.framework.*;

public class CustomerRentalFacade extends AbstractBizCtrl implements ICustomerRentalFacade
{
    public CustomerRentalFacade()
    {
        super();
        registerInterface(ICustomerRentalFacade.class, this);
    }
    public CustomerRentalFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ICustomerRentalFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("FE7EE905");
    }
    private CustomerRentalFacadeController getController() throws BOSException
    {
        return (CustomerRentalFacadeController)getBizController();
    }
    /**
     *getCustomerRentalList-User defined method
     *@param param 参数集合
     *@return
     */
    public Map getCustomerRentalList(Map param) throws BOSException
    {
        try {
            return getController().getCustomerRentalList(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCustomerRentalListList-User defined method
     *@param param 参数集合
     *@return
     */
    public Map getCustomerRentalListList(Map param) throws BOSException
    {
        try {
            return getController().getCustomerRentalListList(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCustomerRentalOtherPayList-User defined method
     *@param param 参数集合
     *@return
     */
    public Map getCustomerRentalOtherPayList(Map param) throws BOSException
    {
        try {
            return getController().getCustomerRentalOtherPayList(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}