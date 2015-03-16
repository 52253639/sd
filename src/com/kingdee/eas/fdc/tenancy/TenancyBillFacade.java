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

public class TenancyBillFacade extends AbstractBizCtrl implements ITenancyBillFacade
{
    public TenancyBillFacade()
    {
        super();
        registerInterface(ITenancyBillFacade.class, this);
    }
    public TenancyBillFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ITenancyBillFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("CA3F5C18");
    }
    private TenancyBillFacadeController getController() throws BOSException
    {
        return (TenancyBillFacadeController)getBizController();
    }
    /**
     *取得租赁合同数据-User defined method
     *@param param 参数集合
     *@return
     */
    public Map getTenancyBill(Map param) throws BOSException
    {
        try {
            return getController().getTenancyBill(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}