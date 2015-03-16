package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SwapRoomReasonFactory
{
    private SwapRoomReasonFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISwapRoomReason getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISwapRoomReason)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("60D4CC8D") ,com.kingdee.eas.fdc.sellhouse.ISwapRoomReason.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISwapRoomReason getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISwapRoomReason)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("60D4CC8D") ,com.kingdee.eas.fdc.sellhouse.ISwapRoomReason.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISwapRoomReason getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISwapRoomReason)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("60D4CC8D"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISwapRoomReason getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISwapRoomReason)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("60D4CC8D"));
    }
}