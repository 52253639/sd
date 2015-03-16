package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DepConPayPlanSplitEntryFactory
{
    private DepConPayPlanSplitEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IDepConPayPlanSplitEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IDepConPayPlanSplitEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("339A63A7") ,com.kingdee.eas.fdc.finance.IDepConPayPlanSplitEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IDepConPayPlanSplitEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IDepConPayPlanSplitEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("339A63A7") ,com.kingdee.eas.fdc.finance.IDepConPayPlanSplitEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IDepConPayPlanSplitEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IDepConPayPlanSplitEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("339A63A7"));
    }
    public static com.kingdee.eas.fdc.finance.IDepConPayPlanSplitEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IDepConPayPlanSplitEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("339A63A7"));
    }
}