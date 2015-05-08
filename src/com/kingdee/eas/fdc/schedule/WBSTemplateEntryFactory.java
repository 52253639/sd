package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WBSTemplateEntryFactory
{
    private WBSTemplateEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IWBSTemplateEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IWBSTemplateEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7E1CDDC6") ,com.kingdee.eas.fdc.schedule.IWBSTemplateEntry.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IWBSTemplateEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IWBSTemplateEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7E1CDDC6") ,com.kingdee.eas.fdc.schedule.IWBSTemplateEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IWBSTemplateEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IWBSTemplateEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7E1CDDC6"));
    }
    public static com.kingdee.eas.fdc.schedule.IWBSTemplateEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IWBSTemplateEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7E1CDDC6"));
    }
}