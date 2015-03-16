package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InvitePreSplitFactory
{
    private InvitePreSplitFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInvitePreSplit getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInvitePreSplit)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1F865484") ,com.kingdee.eas.fdc.invite.IInvitePreSplit.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInvitePreSplit getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInvitePreSplit)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1F865484") ,com.kingdee.eas.fdc.invite.IInvitePreSplit.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInvitePreSplit getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInvitePreSplit)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1F865484"));
    }
    public static com.kingdee.eas.fdc.invite.IInvitePreSplit getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInvitePreSplit)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1F865484"));
    }
}