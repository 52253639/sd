package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SinObligateRoomsEntryFactory
{
    private SinObligateRoomsEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ISinObligateRoomsEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ISinObligateRoomsEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4977CB04") ,com.kingdee.eas.fdc.tenancy.ISinObligateRoomsEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ISinObligateRoomsEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ISinObligateRoomsEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4977CB04") ,com.kingdee.eas.fdc.tenancy.ISinObligateRoomsEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ISinObligateRoomsEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ISinObligateRoomsEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4977CB04"));
    }
    public static com.kingdee.eas.fdc.tenancy.ISinObligateRoomsEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ISinObligateRoomsEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4977CB04"));
    }
}