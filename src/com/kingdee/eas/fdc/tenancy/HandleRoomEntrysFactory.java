package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class HandleRoomEntrysFactory
{
    private HandleRoomEntrysFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IHandleRoomEntrys getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IHandleRoomEntrys)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("89BC1AED") ,com.kingdee.eas.fdc.tenancy.IHandleRoomEntrys.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IHandleRoomEntrys getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IHandleRoomEntrys)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("89BC1AED") ,com.kingdee.eas.fdc.tenancy.IHandleRoomEntrys.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IHandleRoomEntrys getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IHandleRoomEntrys)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("89BC1AED"));
    }
    public static com.kingdee.eas.fdc.tenancy.IHandleRoomEntrys getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IHandleRoomEntrys)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("89BC1AED"));
    }
}