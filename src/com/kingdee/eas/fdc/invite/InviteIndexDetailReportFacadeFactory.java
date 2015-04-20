package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteIndexDetailReportFacadeFactory
{
    private InviteIndexDetailReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteIndexDetailReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteIndexDetailReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("44D61864") ,com.kingdee.eas.fdc.invite.IInviteIndexDetailReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteIndexDetailReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteIndexDetailReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("44D61864") ,com.kingdee.eas.fdc.invite.IInviteIndexDetailReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteIndexDetailReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteIndexDetailReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("44D61864"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteIndexDetailReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteIndexDetailReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("44D61864"));
    }
}