package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class NoiseFactory
{
    private NoiseFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.INoise getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.INoise)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7749D11F") ,com.kingdee.eas.fdc.sellhouse.INoise.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.INoise getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.INoise)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7749D11F") ,com.kingdee.eas.fdc.sellhouse.INoise.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.INoise getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.INoise)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7749D11F"));
    }
    public static com.kingdee.eas.fdc.sellhouse.INoise getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.INoise)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7749D11F"));
    }
}