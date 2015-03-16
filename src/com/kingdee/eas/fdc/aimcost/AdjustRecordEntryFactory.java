package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AdjustRecordEntryFactory
{
    private AdjustRecordEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IAdjustRecordEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAdjustRecordEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("52FA927D") ,com.kingdee.eas.fdc.aimcost.IAdjustRecordEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IAdjustRecordEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAdjustRecordEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("52FA927D") ,com.kingdee.eas.fdc.aimcost.IAdjustRecordEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IAdjustRecordEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAdjustRecordEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("52FA927D"));
    }
    public static com.kingdee.eas.fdc.aimcost.IAdjustRecordEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAdjustRecordEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("52FA927D"));
    }
}