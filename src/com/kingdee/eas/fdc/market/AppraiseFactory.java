package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AppraiseFactory
{
    private AppraiseFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IAppraise getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IAppraise)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("77B9DF3C") ,com.kingdee.eas.fdc.market.IAppraise.class);
    }
    
    public static com.kingdee.eas.fdc.market.IAppraise getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IAppraise)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("77B9DF3C") ,com.kingdee.eas.fdc.market.IAppraise.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IAppraise getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IAppraise)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("77B9DF3C"));
    }
    public static com.kingdee.eas.fdc.market.IAppraise getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IAppraise)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("77B9DF3C"));
    }
}