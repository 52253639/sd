package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteEntSuppChkBillEntryFactory
{
    private InviteEntSuppChkBillEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteEntSuppChkBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteEntSuppChkBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F34CDE45") ,com.kingdee.eas.fdc.invite.IInviteEntSuppChkBillEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteEntSuppChkBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteEntSuppChkBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F34CDE45") ,com.kingdee.eas.fdc.invite.IInviteEntSuppChkBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteEntSuppChkBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteEntSuppChkBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F34CDE45"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteEntSuppChkBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteEntSuppChkBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F34CDE45"));
    }
}