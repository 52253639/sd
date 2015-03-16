package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AttachReportEntryFactory
{
    private AttachReportEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IAttachReportEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAttachReportEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C6151975") ,com.kingdee.eas.fdc.invite.IAttachReportEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IAttachReportEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAttachReportEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C6151975") ,com.kingdee.eas.fdc.invite.IAttachReportEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IAttachReportEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAttachReportEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C6151975"));
    }
    public static com.kingdee.eas.fdc.invite.IAttachReportEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAttachReportEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C6151975"));
    }
}