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

public class AppAmountManageReportFacade extends CommRptBase implements IAppAmountManageReportFacade
{
    public AppAmountManageReportFacade()
    {
        super();
        registerInterface(IAppAmountManageReportFacade.class, this);
    }
    public AppAmountManageReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IAppAmountManageReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F2E714E0");
    }
    private AppAmountManageReportFacadeController getController() throws BOSException
    {
        return (AppAmountManageReportFacadeController)getBizController();
    }
}