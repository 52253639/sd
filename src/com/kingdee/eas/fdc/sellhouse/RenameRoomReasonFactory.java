package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RenameRoomReasonFactory
{
    private RenameRoomReasonFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRenameRoomReason getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRenameRoomReason)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("96BE9DB8") ,com.kingdee.eas.fdc.sellhouse.IRenameRoomReason.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRenameRoomReason getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRenameRoomReason)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("96BE9DB8") ,com.kingdee.eas.fdc.sellhouse.IRenameRoomReason.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRenameRoomReason getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRenameRoomReason)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("96BE9DB8"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRenameRoomReason getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRenameRoomReason)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("96BE9DB8"));
    }
}