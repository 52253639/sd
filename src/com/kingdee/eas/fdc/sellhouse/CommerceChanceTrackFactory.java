package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CommerceChanceTrackFactory
{
    private CommerceChanceTrackFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ICommerceChanceTrack getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICommerceChanceTrack)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B0E101E1") ,com.kingdee.eas.fdc.sellhouse.ICommerceChanceTrack.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ICommerceChanceTrack getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICommerceChanceTrack)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B0E101E1") ,com.kingdee.eas.fdc.sellhouse.ICommerceChanceTrack.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ICommerceChanceTrack getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICommerceChanceTrack)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B0E101E1"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ICommerceChanceTrack getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICommerceChanceTrack)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B0E101E1"));
    }
}