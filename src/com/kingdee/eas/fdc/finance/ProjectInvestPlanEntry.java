package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;

public class ProjectInvestPlanEntry extends CoreBillEntryBase implements IProjectInvestPlanEntry
{
    public ProjectInvestPlanEntry()
    {
        super();
        registerInterface(IProjectInvestPlanEntry.class, this);
    }
    public ProjectInvestPlanEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IProjectInvestPlanEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("480EB9CA");
    }
    private ProjectInvestPlanEntryController getController() throws BOSException
    {
        return (ProjectInvestPlanEntryController)getBizController();
    }
}