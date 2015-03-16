package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ValuePlanEntryFactory
{
    private ValuePlanEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IValuePlanEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IValuePlanEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("95632069") ,com.kingdee.eas.fdc.market.IValuePlanEntry.class);
    }
    
    public static com.kingdee.eas.fdc.market.IValuePlanEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IValuePlanEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("95632069") ,com.kingdee.eas.fdc.market.IValuePlanEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IValuePlanEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IValuePlanEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("95632069"));
    }
    public static com.kingdee.eas.fdc.market.IValuePlanEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IValuePlanEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("95632069"));
    }
}