package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CostIndexConfigFactory
{
    private CostIndexConfigFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.ICostIndexConfig getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICostIndexConfig)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BFD03670") ,com.kingdee.eas.fdc.aimcost.ICostIndexConfig.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.ICostIndexConfig getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICostIndexConfig)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BFD03670") ,com.kingdee.eas.fdc.aimcost.ICostIndexConfig.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.ICostIndexConfig getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICostIndexConfig)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BFD03670"));
    }
    public static com.kingdee.eas.fdc.aimcost.ICostIndexConfig getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICostIndexConfig)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BFD03670"));
    }
}