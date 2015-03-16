package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OrgUnitMonthPlanGatherDateEntryFactory
{
    private OrgUnitMonthPlanGatherDateEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IOrgUnitMonthPlanGatherDateEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IOrgUnitMonthPlanGatherDateEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("730F8C59") ,com.kingdee.eas.fdc.finance.IOrgUnitMonthPlanGatherDateEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IOrgUnitMonthPlanGatherDateEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IOrgUnitMonthPlanGatherDateEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("730F8C59") ,com.kingdee.eas.fdc.finance.IOrgUnitMonthPlanGatherDateEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IOrgUnitMonthPlanGatherDateEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IOrgUnitMonthPlanGatherDateEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("730F8C59"));
    }
    public static com.kingdee.eas.fdc.finance.IOrgUnitMonthPlanGatherDateEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IOrgUnitMonthPlanGatherDateEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("730F8C59"));
    }
}