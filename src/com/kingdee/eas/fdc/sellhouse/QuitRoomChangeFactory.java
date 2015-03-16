package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class QuitRoomChangeFactory
{
    private QuitRoomChangeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IQuitRoomChange getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IQuitRoomChange)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("37C4A3B5") ,com.kingdee.eas.fdc.sellhouse.IQuitRoomChange.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IQuitRoomChange getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IQuitRoomChange)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("37C4A3B5") ,com.kingdee.eas.fdc.sellhouse.IQuitRoomChange.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IQuitRoomChange getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IQuitRoomChange)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("37C4A3B5"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IQuitRoomChange getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IQuitRoomChange)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("37C4A3B5"));
    }
}