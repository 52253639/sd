package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CommerceRoomEntryFactory
{
    private CommerceRoomEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ICommerceRoomEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICommerceRoomEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E9CCEE41") ,com.kingdee.eas.fdc.sellhouse.ICommerceRoomEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ICommerceRoomEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICommerceRoomEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E9CCEE41") ,com.kingdee.eas.fdc.sellhouse.ICommerceRoomEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ICommerceRoomEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICommerceRoomEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E9CCEE41"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ICommerceRoomEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICommerceRoomEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E9CCEE41"));
    }
}