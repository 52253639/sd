package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCBudgetPeriodFactory
{
    private FDCBudgetPeriodFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCBudgetPeriod getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCBudgetPeriod)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EA65907C") ,com.kingdee.eas.fdc.finance.IFDCBudgetPeriod.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCBudgetPeriod getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCBudgetPeriod)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EA65907C") ,com.kingdee.eas.fdc.finance.IFDCBudgetPeriod.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCBudgetPeriod getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCBudgetPeriod)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EA65907C"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCBudgetPeriod getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCBudgetPeriod)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EA65907C"));
    }
}