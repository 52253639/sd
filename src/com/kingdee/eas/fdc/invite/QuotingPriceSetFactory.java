package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class QuotingPriceSetFactory
{
    private QuotingPriceSetFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IQuotingPriceSet getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IQuotingPriceSet)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("27BD3A0E") ,com.kingdee.eas.fdc.invite.IQuotingPriceSet.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IQuotingPriceSet getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IQuotingPriceSet)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("27BD3A0E") ,com.kingdee.eas.fdc.invite.IQuotingPriceSet.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IQuotingPriceSet getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IQuotingPriceSet)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("27BD3A0E"));
    }
    public static com.kingdee.eas.fdc.invite.IQuotingPriceSet getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IQuotingPriceSet)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("27BD3A0E"));
    }
}