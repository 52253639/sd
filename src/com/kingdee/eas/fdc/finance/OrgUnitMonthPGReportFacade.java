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

public class OrgUnitMonthPGReportFacade extends CommRptBase implements IOrgUnitMonthPGReportFacade
{
    public OrgUnitMonthPGReportFacade()
    {
        super();
        registerInterface(IOrgUnitMonthPGReportFacade.class, this);
    }
    public OrgUnitMonthPGReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IOrgUnitMonthPGReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9F5AE1AC");
    }
    private OrgUnitMonthPGReportFacadeController getController() throws BOSException
    {
        return (OrgUnitMonthPGReportFacadeController)getBizController();
    }
}