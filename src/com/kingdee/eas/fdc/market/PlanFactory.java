package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PlanFactory
{
    private PlanFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IPlan getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IPlan)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("20ECA07A") ,com.kingdee.eas.fdc.market.IPlan.class);
    }
    
    public static com.kingdee.eas.fdc.market.IPlan getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IPlan)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("20ECA07A") ,com.kingdee.eas.fdc.market.IPlan.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IPlan getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IPlan)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("20ECA07A"));
    }
    public static com.kingdee.eas.fdc.market.IPlan getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IPlan)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("20ECA07A"));
    }
}