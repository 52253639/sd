package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class QuotingPriceGatherFactory
{
    private QuotingPriceGatherFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IQuotingPriceGather getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IQuotingPriceGather)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("68D5F0CF") ,com.kingdee.eas.fdc.invite.IQuotingPriceGather.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IQuotingPriceGather getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IQuotingPriceGather)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("68D5F0CF") ,com.kingdee.eas.fdc.invite.IQuotingPriceGather.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IQuotingPriceGather getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IQuotingPriceGather)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("68D5F0CF"));
    }
    public static com.kingdee.eas.fdc.invite.IQuotingPriceGather getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IQuotingPriceGather)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("68D5F0CF"));
    }
}