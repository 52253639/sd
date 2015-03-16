package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MortagageControlEntryFactory
{
    private MortagageControlEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IMortagageControlEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMortagageControlEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("57C7A7EF") ,com.kingdee.eas.fdc.sellhouse.IMortagageControlEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IMortagageControlEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMortagageControlEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("57C7A7EF") ,com.kingdee.eas.fdc.sellhouse.IMortagageControlEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IMortagageControlEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMortagageControlEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("57C7A7EF"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IMortagageControlEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMortagageControlEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("57C7A7EF"));
    }
}