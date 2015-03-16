package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ValuePlanDetialEntryFactory
{
    private ValuePlanDetialEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IValuePlanDetialEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IValuePlanDetialEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2D4B1A48") ,com.kingdee.eas.fdc.market.IValuePlanDetialEntry.class);
    }
    
    public static com.kingdee.eas.fdc.market.IValuePlanDetialEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IValuePlanDetialEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2D4B1A48") ,com.kingdee.eas.fdc.market.IValuePlanDetialEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IValuePlanDetialEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IValuePlanDetialEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2D4B1A48"));
    }
    public static com.kingdee.eas.fdc.market.IValuePlanDetialEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IValuePlanDetialEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2D4B1A48"));
    }
}