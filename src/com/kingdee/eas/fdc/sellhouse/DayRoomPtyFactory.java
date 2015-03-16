package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DayRoomPtyFactory
{
    private DayRoomPtyFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IDayRoomPty getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDayRoomPty)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A53F25F9") ,com.kingdee.eas.fdc.sellhouse.IDayRoomPty.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IDayRoomPty getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDayRoomPty)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A53F25F9") ,com.kingdee.eas.fdc.sellhouse.IDayRoomPty.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IDayRoomPty getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDayRoomPty)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A53F25F9"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IDayRoomPty getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDayRoomPty)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A53F25F9"));
    }
}