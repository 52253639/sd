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

public class ArrearsFacade extends AbstractBizCtrl implements IArrearsFacade
{
    public ArrearsFacade()
    {
        super();
        registerInterface(IArrearsFacade.class, this);
    }
    public ArrearsFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IArrearsFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("628EA4EF");
    }
    private ArrearsFacadeController getController() throws BOSException
    {
        return (ArrearsFacadeController)getBizController();
    }
    /**
     *getArrearsList-User defined method
     *@param param 参数集合
     *@return
     */
    public Map getArrearsList(Map param) throws BOSException
    {
        try {
            return getController().getArrearsList(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}