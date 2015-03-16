package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomPropertyReportFactory
{
    private RoomPropertyReportFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IRoomPropertyReport getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRoomPropertyReport)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("80C3D68D") ,com.kingdee.eas.fdc.tenancy.IRoomPropertyReport.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IRoomPropertyReport getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRoomPropertyReport)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("80C3D68D") ,com.kingdee.eas.fdc.tenancy.IRoomPropertyReport.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IRoomPropertyReport getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRoomPropertyReport)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("80C3D68D"));
    }
    public static com.kingdee.eas.fdc.tenancy.IRoomPropertyReport getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRoomPropertyReport)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("80C3D68D"));
    }
}