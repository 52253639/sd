package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CheckNodeFactory
{
    private CheckNodeFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.ICheckNode getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ICheckNode)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6A0C0814") ,com.kingdee.eas.fdc.schedule.ICheckNode.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.ICheckNode getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ICheckNode)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6A0C0814") ,com.kingdee.eas.fdc.schedule.ICheckNode.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.ICheckNode getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ICheckNode)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6A0C0814"));
    }
    public static com.kingdee.eas.fdc.schedule.ICheckNode getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ICheckNode)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6A0C0814"));
    }
}