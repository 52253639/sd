package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ScalingRuleFactory
{
    private ScalingRuleFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IScalingRule getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IScalingRule)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9EB3C97F") ,com.kingdee.eas.fdc.invite.IScalingRule.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IScalingRule getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IScalingRule)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9EB3C97F") ,com.kingdee.eas.fdc.invite.IScalingRule.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IScalingRule getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IScalingRule)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9EB3C97F"));
    }
    public static com.kingdee.eas.fdc.invite.IScalingRule getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IScalingRule)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9EB3C97F"));
    }
}