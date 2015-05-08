package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCMonthTaskEntryFactory
{
    private FDCMonthTaskEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IFDCMonthTaskEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCMonthTaskEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A3CB94FC") ,com.kingdee.eas.fdc.schedule.IFDCMonthTaskEntry.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IFDCMonthTaskEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCMonthTaskEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A3CB94FC") ,com.kingdee.eas.fdc.schedule.IFDCMonthTaskEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IFDCMonthTaskEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCMonthTaskEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A3CB94FC"));
    }
    public static com.kingdee.eas.fdc.schedule.IFDCMonthTaskEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCMonthTaskEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A3CB94FC"));
    }
}