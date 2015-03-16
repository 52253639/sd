package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.sellhouse.app.*;
import java.util.Map;
import com.kingdee.bos.framework.*;

public class MonthSellReportFacade extends AbstractBizCtrl implements IMonthSellReportFacade
{
    public MonthSellReportFacade()
    {
        super();
        registerInterface(IMonthSellReportFacade.class, this);
    }
    public MonthSellReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IMonthSellReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("7B985A05");
    }
    private MonthSellReportFacadeController getController() throws BOSException
    {
        return (MonthSellReportFacadeController)getBizController();
    }
    /**
     *获取前端数据-User defined method
     *@param params params
     *@return
     */
    public Map getAllData(Map params) throws BOSException, EASBizException
    {
        try {
            return getController().getAllData(getContext(), params);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}