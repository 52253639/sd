package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CommerceChangeRoomFactory
{
    private CommerceChangeRoomFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ICommerceChangeRoom getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICommerceChangeRoom)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B1B06801") ,com.kingdee.eas.fdc.sellhouse.ICommerceChangeRoom.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ICommerceChangeRoom getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICommerceChangeRoom)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B1B06801") ,com.kingdee.eas.fdc.sellhouse.ICommerceChangeRoom.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ICommerceChangeRoom getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICommerceChangeRoom)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B1B06801"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ICommerceChangeRoom getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICommerceChangeRoom)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B1B06801"));
    }
}