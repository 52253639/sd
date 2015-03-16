package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AreaRequirementFactory
{
    private AreaRequirementFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IAreaRequirement getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAreaRequirement)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F96D713B") ,com.kingdee.eas.fdc.sellhouse.IAreaRequirement.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IAreaRequirement getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAreaRequirement)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F96D713B") ,com.kingdee.eas.fdc.sellhouse.IAreaRequirement.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IAreaRequirement getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAreaRequirement)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F96D713B"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IAreaRequirement getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAreaRequirement)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F96D713B"));
    }
}