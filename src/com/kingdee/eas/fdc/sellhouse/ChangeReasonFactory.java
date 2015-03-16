package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChangeReasonFactory
{
    private ChangeReasonFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangeReason getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeReason)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("303B046F") ,com.kingdee.eas.fdc.sellhouse.IChangeReason.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IChangeReason getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeReason)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("303B046F") ,com.kingdee.eas.fdc.sellhouse.IChangeReason.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangeReason getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeReason)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("303B046F"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangeReason getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeReason)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("303B046F"));
    }
}