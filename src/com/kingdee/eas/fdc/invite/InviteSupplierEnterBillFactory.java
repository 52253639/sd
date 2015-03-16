package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteSupplierEnterBillFactory
{
    private InviteSupplierEnterBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteSupplierEnterBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteSupplierEnterBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4675F6E6") ,com.kingdee.eas.fdc.invite.IInviteSupplierEnterBill.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteSupplierEnterBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteSupplierEnterBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4675F6E6") ,com.kingdee.eas.fdc.invite.IInviteSupplierEnterBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteSupplierEnterBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteSupplierEnterBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4675F6E6"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteSupplierEnterBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteSupplierEnterBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4675F6E6"));
    }
}