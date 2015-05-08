package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCSchTaskDispColumnsFactory
{
    private FDCSchTaskDispColumnsFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IFDCSchTaskDispColumns getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCSchTaskDispColumns)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("59E9380D") ,com.kingdee.eas.fdc.schedule.IFDCSchTaskDispColumns.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IFDCSchTaskDispColumns getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCSchTaskDispColumns)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("59E9380D") ,com.kingdee.eas.fdc.schedule.IFDCSchTaskDispColumns.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IFDCSchTaskDispColumns getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCSchTaskDispColumns)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("59E9380D"));
    }
    public static com.kingdee.eas.fdc.schedule.IFDCSchTaskDispColumns getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCSchTaskDispColumns)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("59E9380D"));
    }
}