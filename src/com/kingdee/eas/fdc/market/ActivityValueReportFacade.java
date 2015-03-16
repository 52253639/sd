package com.kingdee.eas.fdc.market;

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
import com.kingdee.eas.fdc.market.app.*;

public class ActivityValueReportFacade extends CommRptBase implements IActivityValueReportFacade
{
    public ActivityValueReportFacade()
    {
        super();
        registerInterface(IActivityValueReportFacade.class, this);
    }
    public ActivityValueReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IActivityValueReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("AFA182FF");
    }
    private ActivityValueReportFacadeController getController() throws BOSException
    {
        return (ActivityValueReportFacadeController)getBizController();
    }
}