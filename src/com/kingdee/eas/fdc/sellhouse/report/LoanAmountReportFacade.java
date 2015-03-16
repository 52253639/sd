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

public class LoanAmountReportFacade extends CommRptBase implements ILoanAmountReportFacade
{
    public LoanAmountReportFacade()
    {
        super();
        registerInterface(ILoanAmountReportFacade.class, this);
    }
    public LoanAmountReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ILoanAmountReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("21D589C2");
    }
    private LoanAmountReportFacadeController getController() throws BOSException
    {
        return (LoanAmountReportFacadeController)getBizController();
    }
}