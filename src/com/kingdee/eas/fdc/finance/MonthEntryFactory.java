package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MonthEntryFactory
{
    private MonthEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IMonthEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IMonthEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D66B4421") ,com.kingdee.eas.fdc.finance.IMonthEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IMonthEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IMonthEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D66B4421") ,com.kingdee.eas.fdc.finance.IMonthEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IMonthEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IMonthEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D66B4421"));
    }
    public static com.kingdee.eas.fdc.finance.IMonthEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IMonthEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D66B4421"));
    }
}