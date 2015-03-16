package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SightRequirementFactory
{
    private SightRequirementFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISightRequirement getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISightRequirement)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7D6FC741") ,com.kingdee.eas.fdc.sellhouse.ISightRequirement.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISightRequirement getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISightRequirement)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7D6FC741") ,com.kingdee.eas.fdc.sellhouse.ISightRequirement.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISightRequirement getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISightRequirement)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7D6FC741"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISightRequirement getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISightRequirement)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7D6FC741"));
    }
}