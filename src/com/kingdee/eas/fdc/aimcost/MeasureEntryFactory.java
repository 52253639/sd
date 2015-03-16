package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MeasureEntryFactory
{
    private MeasureEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IMeasureEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IMeasureEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8A291C8B") ,com.kingdee.eas.fdc.aimcost.IMeasureEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IMeasureEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IMeasureEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8A291C8B") ,com.kingdee.eas.fdc.aimcost.IMeasureEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IMeasureEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IMeasureEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8A291C8B"));
    }
    public static com.kingdee.eas.fdc.aimcost.IMeasureEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IMeasureEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8A291C8B"));
    }
}