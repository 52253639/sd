package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CostIndexEntryFactory
{
    private CostIndexEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.ICostIndexEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICostIndexEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("37D83784") ,com.kingdee.eas.fdc.aimcost.ICostIndexEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.ICostIndexEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICostIndexEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("37D83784") ,com.kingdee.eas.fdc.aimcost.ICostIndexEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.ICostIndexEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICostIndexEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("37D83784"));
    }
    public static com.kingdee.eas.fdc.aimcost.ICostIndexEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICostIndexEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("37D83784"));
    }
}