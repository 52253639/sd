package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MonthPlanExcuteFacadeFactory
{
    private MonthPlanExcuteFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IMonthPlanExcuteFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IMonthPlanExcuteFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1B36308A") ,com.kingdee.eas.fdc.finance.IMonthPlanExcuteFacade.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IMonthPlanExcuteFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IMonthPlanExcuteFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1B36308A") ,com.kingdee.eas.fdc.finance.IMonthPlanExcuteFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IMonthPlanExcuteFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IMonthPlanExcuteFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1B36308A"));
    }
    public static com.kingdee.eas.fdc.finance.IMonthPlanExcuteFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IMonthPlanExcuteFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1B36308A"));
    }
}