package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class QuotingPriceAppraiseFactory
{
    private QuotingPriceAppraiseFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IQuotingPriceAppraise getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IQuotingPriceAppraise)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2F0B083F") ,com.kingdee.eas.fdc.invite.IQuotingPriceAppraise.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IQuotingPriceAppraise getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IQuotingPriceAppraise)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2F0B083F") ,com.kingdee.eas.fdc.invite.IQuotingPriceAppraise.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IQuotingPriceAppraise getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IQuotingPriceAppraise)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2F0B083F"));
    }
    public static com.kingdee.eas.fdc.invite.IQuotingPriceAppraise getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IQuotingPriceAppraise)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2F0B083F"));
    }
}