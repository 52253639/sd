package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomPropertyMainFactory
{
    private RoomPropertyMainFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IRoomPropertyMain getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRoomPropertyMain)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("CB1D0952") ,com.kingdee.eas.fdc.tenancy.IRoomPropertyMain.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IRoomPropertyMain getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRoomPropertyMain)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("CB1D0952") ,com.kingdee.eas.fdc.tenancy.IRoomPropertyMain.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IRoomPropertyMain getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRoomPropertyMain)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("CB1D0952"));
    }
    public static com.kingdee.eas.fdc.tenancy.IRoomPropertyMain getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRoomPropertyMain)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("CB1D0952"));
    }
}