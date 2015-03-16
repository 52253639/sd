package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OrgUnitMonthPlanGatherEntryFactory
{
    private OrgUnitMonthPlanGatherEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IOrgUnitMonthPlanGatherEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IOrgUnitMonthPlanGatherEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C89491E7") ,com.kingdee.eas.fdc.finance.IOrgUnitMonthPlanGatherEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IOrgUnitMonthPlanGatherEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IOrgUnitMonthPlanGatherEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C89491E7") ,com.kingdee.eas.fdc.finance.IOrgUnitMonthPlanGatherEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IOrgUnitMonthPlanGatherEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IOrgUnitMonthPlanGatherEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C89491E7"));
    }
    public static com.kingdee.eas.fdc.finance.IOrgUnitMonthPlanGatherEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IOrgUnitMonthPlanGatherEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C89491E7"));
    }
}