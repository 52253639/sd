package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteEntSuppChkBillFactory
{
    private InviteEntSuppChkBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteEntSuppChkBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteEntSuppChkBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3F160E2D") ,com.kingdee.eas.fdc.invite.IInviteEntSuppChkBill.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteEntSuppChkBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteEntSuppChkBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3F160E2D") ,com.kingdee.eas.fdc.invite.IInviteEntSuppChkBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteEntSuppChkBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteEntSuppChkBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3F160E2D"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteEntSuppChkBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteEntSuppChkBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3F160E2D"));
    }
}