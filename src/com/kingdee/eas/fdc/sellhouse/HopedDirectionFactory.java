package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class HopedDirectionFactory
{
    private HopedDirectionFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IHopedDirection getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IHopedDirection)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4505BD52") ,com.kingdee.eas.fdc.sellhouse.IHopedDirection.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IHopedDirection getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IHopedDirection)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4505BD52") ,com.kingdee.eas.fdc.sellhouse.IHopedDirection.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IHopedDirection getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IHopedDirection)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4505BD52"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IHopedDirection getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IHopedDirection)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4505BD52"));
    }
}