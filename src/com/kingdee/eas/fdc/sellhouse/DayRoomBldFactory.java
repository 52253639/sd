package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DayRoomBldFactory
{
    private DayRoomBldFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IDayRoomBld getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDayRoomBld)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A53EF05E") ,com.kingdee.eas.fdc.sellhouse.IDayRoomBld.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IDayRoomBld getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDayRoomBld)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A53EF05E") ,com.kingdee.eas.fdc.sellhouse.IDayRoomBld.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IDayRoomBld getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDayRoomBld)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A53EF05E"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IDayRoomBld getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDayRoomBld)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A53EF05E"));
    }
}