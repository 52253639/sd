package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ConPayPlanSplitFactory
{
    private ConPayPlanSplitFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IConPayPlanSplit getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanSplit)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7B145B5C") ,com.kingdee.eas.fdc.finance.IConPayPlanSplit.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IConPayPlanSplit getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanSplit)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7B145B5C") ,com.kingdee.eas.fdc.finance.IConPayPlanSplit.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IConPayPlanSplit getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanSplit)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7B145B5C"));
    }
    public static com.kingdee.eas.fdc.finance.IConPayPlanSplit getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanSplit)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7B145B5C"));
    }
}