package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class GlobalTaskNodeFactory
{
    private GlobalTaskNodeFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IGlobalTaskNode getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IGlobalTaskNode)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1F89DF40") ,com.kingdee.eas.fdc.schedule.IGlobalTaskNode.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IGlobalTaskNode getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IGlobalTaskNode)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1F89DF40") ,com.kingdee.eas.fdc.schedule.IGlobalTaskNode.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IGlobalTaskNode getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IGlobalTaskNode)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1F89DF40"));
    }
    public static com.kingdee.eas.fdc.schedule.IGlobalTaskNode getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IGlobalTaskNode)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1F89DF40"));
    }
}