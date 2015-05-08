package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WBSCodeRuleEntryFactory
{
    private WBSCodeRuleEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IWBSCodeRuleEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IWBSCodeRuleEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A09A2117") ,com.kingdee.eas.fdc.schedule.IWBSCodeRuleEntry.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IWBSCodeRuleEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IWBSCodeRuleEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A09A2117") ,com.kingdee.eas.fdc.schedule.IWBSCodeRuleEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IWBSCodeRuleEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IWBSCodeRuleEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A09A2117"));
    }
    public static com.kingdee.eas.fdc.schedule.IWBSCodeRuleEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IWBSCodeRuleEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A09A2117"));
    }
}