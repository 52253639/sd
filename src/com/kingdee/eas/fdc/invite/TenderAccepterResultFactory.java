package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TenderAccepterResultFactory
{
    private TenderAccepterResultFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.ITenderAccepterResult getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ITenderAccepterResult)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6C66BD4A") ,com.kingdee.eas.fdc.invite.ITenderAccepterResult.class);
    }
    
    public static com.kingdee.eas.fdc.invite.ITenderAccepterResult getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ITenderAccepterResult)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6C66BD4A") ,com.kingdee.eas.fdc.invite.ITenderAccepterResult.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.ITenderAccepterResult getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ITenderAccepterResult)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6C66BD4A"));
    }
    public static com.kingdee.eas.fdc.invite.ITenderAccepterResult getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ITenderAccepterResult)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6C66BD4A"));
    }
}