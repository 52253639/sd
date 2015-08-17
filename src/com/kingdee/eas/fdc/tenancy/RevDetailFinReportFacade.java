package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.report.CommRptBase;

public class RevDetailFinReportFacade extends CommRptBase implements IRevDetailFinReportFacade
{
    public RevDetailFinReportFacade()
    {
        super();
        registerInterface(IRevDetailFinReportFacade.class, this);
    }
    public RevDetailFinReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IRevDetailFinReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E72527CE");
    }
    private RevDetailFinReportFacadeController getController() throws BOSException
    {
        return (RevDetailFinReportFacadeController)getBizController();
    }
}