package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.report.CommRptBase;

public class MarketCostReportFacade extends CommRptBase implements IMarketCostReportFacade
{
    public MarketCostReportFacade()
    {
        super();
        registerInterface(IMarketCostReportFacade.class, this);
    }
    public MarketCostReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketCostReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("FA359386");
    }
    private MarketCostReportFacadeController getController() throws BOSException
    {
        return (MarketCostReportFacadeController)getBizController();
    }
}