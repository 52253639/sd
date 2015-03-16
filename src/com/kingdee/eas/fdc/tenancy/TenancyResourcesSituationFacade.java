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

public class TenancyResourcesSituationFacade extends AbstractBizCtrl implements ITenancyResourcesSituationFacade
{
    public TenancyResourcesSituationFacade()
    {
        super();
        registerInterface(ITenancyResourcesSituationFacade.class, this);
    }
    public TenancyResourcesSituationFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ITenancyResourcesSituationFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E362F3AA");
    }
    private TenancyResourcesSituationFacadeController getController() throws BOSException
    {
        return (TenancyResourcesSituationFacadeController)getBizController();
    }
    /**
     *获取报表数据-User defined method
     *@param param 参数
     *@return
     */
    public Map getResult(Map param) throws BOSException
    {
        try {
            return getController().getResult(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}