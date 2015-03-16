package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OrgUnitMonthPlanGatherFactory
{
    private OrgUnitMonthPlanGatherFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IOrgUnitMonthPlanGather getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IOrgUnitMonthPlanGather)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2A911D4B") ,com.kingdee.eas.fdc.finance.IOrgUnitMonthPlanGather.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IOrgUnitMonthPlanGather getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IOrgUnitMonthPlanGather)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2A911D4B") ,com.kingdee.eas.fdc.finance.IOrgUnitMonthPlanGather.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IOrgUnitMonthPlanGather getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IOrgUnitMonthPlanGather)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2A911D4B"));
    }
    public static com.kingdee.eas.fdc.finance.IOrgUnitMonthPlanGather getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IOrgUnitMonthPlanGather)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2A911D4B"));
    }
}