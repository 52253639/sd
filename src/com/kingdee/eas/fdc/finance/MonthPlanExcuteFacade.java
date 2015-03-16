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

public class MonthPlanExcuteFacade extends CommRptBase implements IMonthPlanExcuteFacade
{
    public MonthPlanExcuteFacade()
    {
        super();
        registerInterface(IMonthPlanExcuteFacade.class, this);
    }
    public MonthPlanExcuteFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IMonthPlanExcuteFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("1B36308A");
    }
    private MonthPlanExcuteFacadeController getController() throws BOSException
    {
        return (MonthPlanExcuteFacadeController)getBizController();
    }
}