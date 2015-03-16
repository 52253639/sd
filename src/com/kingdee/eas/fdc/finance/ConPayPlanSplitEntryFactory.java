package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ConPayPlanSplitEntryFactory
{
    private ConPayPlanSplitEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IConPayPlanSplitEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanSplitEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3B6A7676") ,com.kingdee.eas.fdc.finance.IConPayPlanSplitEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IConPayPlanSplitEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanSplitEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3B6A7676") ,com.kingdee.eas.fdc.finance.IConPayPlanSplitEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IConPayPlanSplitEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanSplitEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3B6A7676"));
    }
    public static com.kingdee.eas.fdc.finance.IConPayPlanSplitEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanSplitEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3B6A7676"));
    }
}