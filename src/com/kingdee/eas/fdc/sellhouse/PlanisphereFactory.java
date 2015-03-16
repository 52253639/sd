package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PlanisphereFactory
{
    private PlanisphereFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPlanisphere getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPlanisphere)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BC5F4C72") ,com.kingdee.eas.fdc.sellhouse.IPlanisphere.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPlanisphere getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPlanisphere)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BC5F4C72") ,com.kingdee.eas.fdc.sellhouse.IPlanisphere.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPlanisphere getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPlanisphere)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BC5F4C72"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPlanisphere getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPlanisphere)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BC5F4C72"));
    }
}