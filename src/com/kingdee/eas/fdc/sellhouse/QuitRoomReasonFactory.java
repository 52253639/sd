package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class QuitRoomReasonFactory
{
    private QuitRoomReasonFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IQuitRoomReason getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IQuitRoomReason)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("51332249") ,com.kingdee.eas.fdc.sellhouse.IQuitRoomReason.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IQuitRoomReason getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IQuitRoomReason)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("51332249") ,com.kingdee.eas.fdc.sellhouse.IQuitRoomReason.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IQuitRoomReason getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IQuitRoomReason)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("51332249"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IQuitRoomReason getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IQuitRoomReason)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("51332249"));
    }
}