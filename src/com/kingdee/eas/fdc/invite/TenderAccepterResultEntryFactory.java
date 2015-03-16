package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TenderAccepterResultEntryFactory
{
    private TenderAccepterResultEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.ITenderAccepterResultEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ITenderAccepterResultEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("698E1948") ,com.kingdee.eas.fdc.invite.ITenderAccepterResultEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.ITenderAccepterResultEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ITenderAccepterResultEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("698E1948") ,com.kingdee.eas.fdc.invite.ITenderAccepterResultEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.ITenderAccepterResultEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ITenderAccepterResultEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("698E1948"));
    }
    public static com.kingdee.eas.fdc.invite.ITenderAccepterResultEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ITenderAccepterResultEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("698E1948"));
    }
}