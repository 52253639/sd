package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WBSTemplatePrefixEntryFactory
{
    private WBSTemplatePrefixEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IWBSTemplatePrefixEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IWBSTemplatePrefixEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4F1FB754") ,com.kingdee.eas.fdc.schedule.IWBSTemplatePrefixEntry.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IWBSTemplatePrefixEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IWBSTemplatePrefixEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4F1FB754") ,com.kingdee.eas.fdc.schedule.IWBSTemplatePrefixEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IWBSTemplatePrefixEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IWBSTemplatePrefixEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4F1FB754"));
    }
    public static com.kingdee.eas.fdc.schedule.IWBSTemplatePrefixEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IWBSTemplatePrefixEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4F1FB754"));
    }
}