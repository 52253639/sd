package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteChangeFormFactory
{
    private InviteChangeFormFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteChangeForm getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteChangeForm)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("09D18BA1") ,com.kingdee.eas.fdc.invite.IInviteChangeForm.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteChangeForm getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteChangeForm)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("09D18BA1") ,com.kingdee.eas.fdc.invite.IInviteChangeForm.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteChangeForm getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteChangeForm)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("09D18BA1"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteChangeForm getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteChangeForm)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("09D18BA1"));
    }
}