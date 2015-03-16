package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.aimcost.app.*;
import com.kingdee.eas.framework.report.CommRptBase;

public class CostIndexDetialReportFacade extends CommRptBase implements ICostIndexDetialReportFacade
{
    public CostIndexDetialReportFacade()
    {
        super();
        registerInterface(ICostIndexDetialReportFacade.class, this);
    }
    public CostIndexDetialReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ICostIndexDetialReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("2990191D");
    }
    private CostIndexDetialReportFacadeController getController() throws BOSException
    {
        return (CostIndexDetialReportFacadeController)getBizController();
    }
}