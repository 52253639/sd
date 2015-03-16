package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteBackAnalysisEntrysFactory
{
    private InviteBackAnalysisEntrysFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteBackAnalysisEntrys getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteBackAnalysisEntrys)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BBA03311") ,com.kingdee.eas.fdc.invite.IInviteBackAnalysisEntrys.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteBackAnalysisEntrys getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteBackAnalysisEntrys)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BBA03311") ,com.kingdee.eas.fdc.invite.IInviteBackAnalysisEntrys.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteBackAnalysisEntrys getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteBackAnalysisEntrys)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BBA03311"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteBackAnalysisEntrys getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteBackAnalysisEntrys)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BBA03311"));
    }
}