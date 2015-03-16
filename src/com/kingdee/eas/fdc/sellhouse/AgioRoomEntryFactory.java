package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AgioRoomEntryFactory
{
    private AgioRoomEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IAgioRoomEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAgioRoomEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7D3A3AF0") ,com.kingdee.eas.fdc.sellhouse.IAgioRoomEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IAgioRoomEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAgioRoomEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7D3A3AF0") ,com.kingdee.eas.fdc.sellhouse.IAgioRoomEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IAgioRoomEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAgioRoomEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7D3A3AF0"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IAgioRoomEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAgioRoomEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7D3A3AF0"));
    }
}