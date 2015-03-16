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

public class BackAmountOverdueReportFacade extends CommRptBase implements IBackAmountOverdueReportFacade
{
    public BackAmountOverdueReportFacade()
    {
        super();
        registerInterface(IBackAmountOverdueReportFacade.class, this);
    }
    public BackAmountOverdueReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IBackAmountOverdueReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("BFEAB643");
    }
    private BackAmountOverdueReportFacadeController getController() throws BOSException
    {
        return (BackAmountOverdueReportFacadeController)getBizController();
    }
}