package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WBSTemplateTypeFactory
{
    private WBSTemplateTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IWBSTemplateType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IWBSTemplateType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2D62BF66") ,com.kingdee.eas.fdc.schedule.IWBSTemplateType.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IWBSTemplateType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IWBSTemplateType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2D62BF66") ,com.kingdee.eas.fdc.schedule.IWBSTemplateType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IWBSTemplateType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IWBSTemplateType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2D62BF66"));
    }
    public static com.kingdee.eas.fdc.schedule.IWBSTemplateType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IWBSTemplateType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2D62BF66"));
    }
}