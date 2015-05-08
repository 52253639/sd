package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WBSCodeRuleFactory
{
    private WBSCodeRuleFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IWBSCodeRule getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IWBSCodeRule)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("05BAD01B") ,com.kingdee.eas.fdc.schedule.IWBSCodeRule.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IWBSCodeRule getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IWBSCodeRule)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("05BAD01B") ,com.kingdee.eas.fdc.schedule.IWBSCodeRule.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IWBSCodeRule getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IWBSCodeRule)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("05BAD01B"));
    }
    public static com.kingdee.eas.fdc.schedule.IWBSCodeRule getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IWBSCodeRule)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("05BAD01B"));
    }
}