package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TenancyRoomEntryFactory
{
    private TenancyRoomEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyRoomEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyRoomEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B54A7CE0") ,com.kingdee.eas.fdc.tenancy.ITenancyRoomEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ITenancyRoomEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyRoomEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B54A7CE0") ,com.kingdee.eas.fdc.tenancy.ITenancyRoomEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyRoomEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyRoomEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B54A7CE0"));
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyRoomEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyRoomEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B54A7CE0"));
    }
}