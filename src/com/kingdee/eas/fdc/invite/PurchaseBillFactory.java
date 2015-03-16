package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PurchaseBillFactory
{
    private PurchaseBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IPurchaseBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IPurchaseBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("991A21CC") ,com.kingdee.eas.fdc.invite.IPurchaseBill.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IPurchaseBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IPurchaseBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("991A21CC") ,com.kingdee.eas.fdc.invite.IPurchaseBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IPurchaseBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IPurchaseBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("991A21CC"));
    }
    public static com.kingdee.eas.fdc.invite.IPurchaseBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IPurchaseBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("991A21CC"));
    }
}