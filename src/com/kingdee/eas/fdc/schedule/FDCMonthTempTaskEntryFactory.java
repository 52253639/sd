package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCMonthTempTaskEntryFactory
{
    private FDCMonthTempTaskEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IFDCMonthTempTaskEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCMonthTempTaskEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("86AC6008") ,com.kingdee.eas.fdc.schedule.IFDCMonthTempTaskEntry.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IFDCMonthTempTaskEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCMonthTempTaskEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("86AC6008") ,com.kingdee.eas.fdc.schedule.IFDCMonthTempTaskEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IFDCMonthTempTaskEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCMonthTempTaskEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("86AC6008"));
    }
    public static com.kingdee.eas.fdc.schedule.IFDCMonthTempTaskEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCMonthTempTaskEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("86AC6008"));
    }
}