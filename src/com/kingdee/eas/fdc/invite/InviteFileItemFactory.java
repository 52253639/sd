package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteFileItemFactory
{
    private InviteFileItemFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteFileItem getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteFileItem)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("418074FC") ,com.kingdee.eas.fdc.invite.IInviteFileItem.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteFileItem getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteFileItem)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("418074FC") ,com.kingdee.eas.fdc.invite.IInviteFileItem.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteFileItem getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteFileItem)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("418074FC"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteFileItem getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteFileItem)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("418074FC"));
    }
}