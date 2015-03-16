package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CostIndexConfigEntryFactory
{
    private CostIndexConfigEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.ICostIndexConfigEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICostIndexConfigEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8B0067E2") ,com.kingdee.eas.fdc.aimcost.ICostIndexConfigEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.ICostIndexConfigEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICostIndexConfigEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8B0067E2") ,com.kingdee.eas.fdc.aimcost.ICostIndexConfigEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.ICostIndexConfigEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICostIndexConfigEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8B0067E2"));
    }
    public static com.kingdee.eas.fdc.aimcost.ICostIndexConfigEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICostIndexConfigEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8B0067E2"));
    }
}