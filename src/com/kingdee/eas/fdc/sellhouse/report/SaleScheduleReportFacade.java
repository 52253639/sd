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
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.report.CommRptBase;

public class SaleScheduleReportFacade extends CommRptBase implements ISaleScheduleReportFacade
{
    public SaleScheduleReportFacade()
    {
        super();
        registerInterface(ISaleScheduleReportFacade.class, this);
    }
    public SaleScheduleReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ISaleScheduleReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("8FBE2858");
    }
    private SaleScheduleReportFacadeController getController() throws BOSException
    {
        return (SaleScheduleReportFacadeController)getBizController();
    }
}