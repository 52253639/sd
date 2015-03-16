package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OldDealAmountEntryFactory
{
    private OldDealAmountEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IOldDealAmountEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IOldDealAmountEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("AED03F30") ,com.kingdee.eas.fdc.tenancy.IOldDealAmountEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IOldDealAmountEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IOldDealAmountEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("AED03F30") ,com.kingdee.eas.fdc.tenancy.IOldDealAmountEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IOldDealAmountEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IOldDealAmountEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("AED03F30"));
    }
    public static com.kingdee.eas.fdc.tenancy.IOldDealAmountEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IOldDealAmountEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("AED03F30"));
    }
}