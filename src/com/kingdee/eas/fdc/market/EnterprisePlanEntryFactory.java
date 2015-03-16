package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EnterprisePlanEntryFactory
{
    private EnterprisePlanEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IEnterprisePlanEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IEnterprisePlanEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3F0BC6F7") ,com.kingdee.eas.fdc.market.IEnterprisePlanEntry.class);
    }
    
    public static com.kingdee.eas.fdc.market.IEnterprisePlanEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IEnterprisePlanEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3F0BC6F7") ,com.kingdee.eas.fdc.market.IEnterprisePlanEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IEnterprisePlanEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IEnterprisePlanEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3F0BC6F7"));
    }
    public static com.kingdee.eas.fdc.market.IEnterprisePlanEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IEnterprisePlanEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3F0BC6F7"));
    }
}