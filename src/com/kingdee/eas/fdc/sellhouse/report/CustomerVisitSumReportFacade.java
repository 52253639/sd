package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.sellhouse.report.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import java.util.List;
import com.kingdee.eas.framework.report.util.RptParams;

public class CustomerVisitSumReportFacade extends AbstractBizCtrl implements ICustomerVisitSumReportFacade
{
    public CustomerVisitSumReportFacade()
    {
        super();
        registerInterface(ICustomerVisitSumReportFacade.class, this);
    }
    public CustomerVisitSumReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ICustomerVisitSumReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4CE73958");
    }
    private CustomerVisitSumReportFacadeController getController() throws BOSException
    {
        return (CustomerVisitSumReportFacadeController)getBizController();
    }
    /**
     *数据填充-User defined method
     *@param params 查询条件
     *@return
     */
    public List getTableList(RptParams params) throws BOSException
    {
        try {
            return getController().getTableList(getContext(), params);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}