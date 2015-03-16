package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TenancyRoomChargeEntryFactory
{
    private TenancyRoomChargeEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyRoomChargeEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyRoomChargeEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F784700C") ,com.kingdee.eas.fdc.tenancy.ITenancyRoomChargeEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ITenancyRoomChargeEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyRoomChargeEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F784700C") ,com.kingdee.eas.fdc.tenancy.ITenancyRoomChargeEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyRoomChargeEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyRoomChargeEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F784700C"));
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyRoomChargeEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyRoomChargeEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F784700C"));
    }
}