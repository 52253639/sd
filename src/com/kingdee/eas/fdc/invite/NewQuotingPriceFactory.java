package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class NewQuotingPriceFactory
{
    private NewQuotingPriceFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.INewQuotingPrice getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewQuotingPrice)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("268DAEAC") ,com.kingdee.eas.fdc.invite.INewQuotingPrice.class);
    }
    
    public static com.kingdee.eas.fdc.invite.INewQuotingPrice getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewQuotingPrice)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("268DAEAC") ,com.kingdee.eas.fdc.invite.INewQuotingPrice.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.INewQuotingPrice getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewQuotingPrice)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("268DAEAC"));
    }
    public static com.kingdee.eas.fdc.invite.INewQuotingPrice getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewQuotingPrice)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("268DAEAC"));
    }
}