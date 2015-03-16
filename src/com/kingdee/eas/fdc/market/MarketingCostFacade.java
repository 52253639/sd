package com.kingdee.eas.fdc.market;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.base.param.ParamException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.report.IVirtualRptBaseFacade;
import com.kingdee.eas.fdc.market.app.*;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.VirtualRptBaseFacade;

public class MarketingCostFacade extends VirtualRptBaseFacade implements IMarketingCostFacade
{
    public MarketingCostFacade()
    {
        super();
        registerInterface(IMarketingCostFacade.class, this);
    }
    public MarketingCostFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketingCostFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("1D8FAC1C");
    }
    private MarketingCostFacadeController getController() throws BOSException
    {
        return (MarketingCostFacadeController)getBizController();
    }
    /**
     *getTableList-User defined method
     *@param params 报表集合
     *@param ids id字符串
     *@return
     */
    public RptParams getTableList(RptParams params, String ids) throws BOSException, ParamException
    {
        try {
            return getController().getTableList(getContext(), params, ids);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}