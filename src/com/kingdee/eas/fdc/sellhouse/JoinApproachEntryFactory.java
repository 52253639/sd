package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class JoinApproachEntryFactory
{
    private JoinApproachEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IJoinApproachEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IJoinApproachEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9086DB25") ,com.kingdee.eas.fdc.sellhouse.IJoinApproachEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IJoinApproachEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IJoinApproachEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9086DB25") ,com.kingdee.eas.fdc.sellhouse.IJoinApproachEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IJoinApproachEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IJoinApproachEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9086DB25"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IJoinApproachEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IJoinApproachEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9086DB25"));
    }
}