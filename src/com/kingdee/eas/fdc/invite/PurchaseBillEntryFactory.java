package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PurchaseBillEntryFactory
{
    private PurchaseBillEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IPurchaseBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IPurchaseBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B72F3606") ,com.kingdee.eas.fdc.invite.IPurchaseBillEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IPurchaseBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IPurchaseBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B72F3606") ,com.kingdee.eas.fdc.invite.IPurchaseBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IPurchaseBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IPurchaseBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B72F3606"));
    }
    public static com.kingdee.eas.fdc.invite.IPurchaseBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IPurchaseBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B72F3606"));
    }
}