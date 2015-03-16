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
import java.util.Map;
import com.kingdee.bos.framework.*;

public class SellProjectReportFacade extends AbstractBizCtrl implements ISellProjectReportFacade
{
    public SellProjectReportFacade()
    {
        super();
        registerInterface(ISellProjectReportFacade.class, this);
    }
    public SellProjectReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ISellProjectReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("14BA2029");
    }
    private SellProjectReportFacadeController getController() throws BOSException
    {
        return (SellProjectReportFacadeController)getBizController();
    }
    /**
     *获取表格数据-User defined method
     *@param map 参数
     *@return
     */
    public Map getTableData(Map map) throws BOSException
    {
        try {
            return getController().getTableData(getContext(), map);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}