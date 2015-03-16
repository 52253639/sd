package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MeasureIncomeEntryFactory
{
    private MeasureIncomeEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IMeasureIncomeEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IMeasureIncomeEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E7D8F5A2") ,com.kingdee.eas.fdc.aimcost.IMeasureIncomeEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IMeasureIncomeEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IMeasureIncomeEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E7D8F5A2") ,com.kingdee.eas.fdc.aimcost.IMeasureIncomeEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IMeasureIncomeEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IMeasureIncomeEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E7D8F5A2"));
    }
    public static com.kingdee.eas.fdc.aimcost.IMeasureIncomeEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IMeasureIncomeEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E7D8F5A2"));
    }
}