package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EnterprisePlanFactory
{
    private EnterprisePlanFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IEnterprisePlan getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IEnterprisePlan)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B290FE3B") ,com.kingdee.eas.fdc.market.IEnterprisePlan.class);
    }
    
    public static com.kingdee.eas.fdc.market.IEnterprisePlan getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IEnterprisePlan)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B290FE3B") ,com.kingdee.eas.fdc.market.IEnterprisePlan.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IEnterprisePlan getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IEnterprisePlan)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B290FE3B"));
    }
    public static com.kingdee.eas.fdc.market.IEnterprisePlan getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IEnterprisePlan)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B290FE3B"));
    }
}