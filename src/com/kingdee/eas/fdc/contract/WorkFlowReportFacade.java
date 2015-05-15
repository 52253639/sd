package com.kingdee.eas.fdc.contract;

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
import com.kingdee.eas.framework.report.CommRptBase;
import com.kingdee.eas.fdc.contract.app.*;

public class WorkFlowReportFacade extends CommRptBase implements IWorkFlowReportFacade
{
    public WorkFlowReportFacade()
    {
        super();
        registerInterface(IWorkFlowReportFacade.class, this);
    }
    public WorkFlowReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IWorkFlowReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("BB901B28");
    }
    private WorkFlowReportFacadeController getController() throws BOSException
    {
        return (WorkFlowReportFacadeController)getBizController();
    }
}