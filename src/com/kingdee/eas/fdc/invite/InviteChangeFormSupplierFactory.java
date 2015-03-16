package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteChangeFormSupplierFactory
{
    private InviteChangeFormSupplierFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteChangeFormSupplier getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteChangeFormSupplier)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B0D6B88D") ,com.kingdee.eas.fdc.invite.IInviteChangeFormSupplier.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteChangeFormSupplier getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteChangeFormSupplier)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B0D6B88D") ,com.kingdee.eas.fdc.invite.IInviteChangeFormSupplier.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteChangeFormSupplier getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteChangeFormSupplier)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B0D6B88D"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteChangeFormSupplier getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteChangeFormSupplier)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B0D6B88D"));
    }
}