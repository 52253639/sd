package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AttachReportFactory
{
    private AttachReportFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IAttachReport getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAttachReport)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("838194FD") ,com.kingdee.eas.fdc.invite.IAttachReport.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IAttachReport getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAttachReport)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("838194FD") ,com.kingdee.eas.fdc.invite.IAttachReport.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IAttachReport getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAttachReport)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("838194FD"));
    }
    public static com.kingdee.eas.fdc.invite.IAttachReport getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAttachReport)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("838194FD"));
    }
}