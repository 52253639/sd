package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PolicyManageEntryFactory
{
    private PolicyManageEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IPolicyManageEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IPolicyManageEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5B0D17CA") ,com.kingdee.eas.fdc.market.IPolicyManageEntry.class);
    }
    
    public static com.kingdee.eas.fdc.market.IPolicyManageEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IPolicyManageEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5B0D17CA") ,com.kingdee.eas.fdc.market.IPolicyManageEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IPolicyManageEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IPolicyManageEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5B0D17CA"));
    }
    public static com.kingdee.eas.fdc.market.IPolicyManageEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IPolicyManageEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5B0D17CA"));
    }
}