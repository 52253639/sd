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

public class CustomerTenancyAreaChangeFacade extends AbstractBizCtrl implements ICustomerTenancyAreaChangeFacade
{
    public CustomerTenancyAreaChangeFacade()
    {
        super();
        registerInterface(ICustomerTenancyAreaChangeFacade.class, this);
    }
    public CustomerTenancyAreaChangeFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ICustomerTenancyAreaChangeFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("2BFC0C30");
    }
    private CustomerTenancyAreaChangeFacadeController getController() throws BOSException
    {
        return (CustomerTenancyAreaChangeFacadeController)getBizController();
    }
    /**
     *getValueMap-User defined method
     *@param ID ID
     *@return
     */
    public Map getValueMap(Map ID) throws BOSException
    {
        try {
            return getController().getValueMap(getContext(), ID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}