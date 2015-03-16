package com.kingdee.eas.fdc.market.report;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.market.report.*;

public class MarketAccountViewReportFacade extends AbstractBizCtrl implements IMarketAccountViewReportFacade
{
    public MarketAccountViewReportFacade()
    {
        super();
        registerInterface(IMarketAccountViewReportFacade.class, this);
    }
    public MarketAccountViewReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketAccountViewReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B26B9ECE");
    }
    private MarketAccountViewReportFacadeController getController() throws BOSException
    {
        return (MarketAccountViewReportFacadeController)getBizController();
    }
    /**
     *营销费用科目分类取数-User defined method
     *@param map 营销费用科目分类取数
     *@return
     */
    public Map getTableList(Map map) throws BOSException
    {
        try {
            return getController().getTableList(getContext(), map);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}