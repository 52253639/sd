package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DealAmountEntryFactory
{
    private DealAmountEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IDealAmountEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IDealAmountEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("185DD625") ,com.kingdee.eas.fdc.tenancy.IDealAmountEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IDealAmountEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IDealAmountEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("185DD625") ,com.kingdee.eas.fdc.tenancy.IDealAmountEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IDealAmountEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IDealAmountEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("185DD625"));
    }
    public static com.kingdee.eas.fdc.tenancy.IDealAmountEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IDealAmountEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("185DD625"));
    }
}