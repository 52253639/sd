package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TenancyOrderRoomEntryFactory
{
    private TenancyOrderRoomEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyOrderRoomEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyOrderRoomEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4D22B1C0") ,com.kingdee.eas.fdc.tenancy.ITenancyOrderRoomEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ITenancyOrderRoomEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyOrderRoomEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4D22B1C0") ,com.kingdee.eas.fdc.tenancy.ITenancyOrderRoomEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyOrderRoomEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyOrderRoomEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4D22B1C0"));
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyOrderRoomEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyOrderRoomEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4D22B1C0"));
    }
}