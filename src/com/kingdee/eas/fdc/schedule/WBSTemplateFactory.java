package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WBSTemplateFactory
{
    private WBSTemplateFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IWBSTemplate getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IWBSTemplate)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EAB2A20C") ,com.kingdee.eas.fdc.schedule.IWBSTemplate.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IWBSTemplate getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IWBSTemplate)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EAB2A20C") ,com.kingdee.eas.fdc.schedule.IWBSTemplate.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IWBSTemplate getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IWBSTemplate)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EAB2A20C"));
    }
    public static com.kingdee.eas.fdc.schedule.IWBSTemplate getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IWBSTemplate)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EAB2A20C"));
    }
}