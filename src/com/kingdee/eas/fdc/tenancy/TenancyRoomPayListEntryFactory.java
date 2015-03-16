package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TenancyRoomPayListEntryFactory
{
    private TenancyRoomPayListEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyRoomPayListEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyRoomPayListEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("31D11A7E") ,com.kingdee.eas.fdc.tenancy.ITenancyRoomPayListEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ITenancyRoomPayListEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyRoomPayListEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("31D11A7E") ,com.kingdee.eas.fdc.tenancy.ITenancyRoomPayListEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyRoomPayListEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyRoomPayListEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("31D11A7E"));
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyRoomPayListEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyRoomPayListEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("31D11A7E"));
    }
}