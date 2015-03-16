package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class NewDealAmountEntryFactory
{
    private NewDealAmountEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.INewDealAmountEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.INewDealAmountEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("736CC297") ,com.kingdee.eas.fdc.tenancy.INewDealAmountEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.INewDealAmountEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.INewDealAmountEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("736CC297") ,com.kingdee.eas.fdc.tenancy.INewDealAmountEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.INewDealAmountEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.INewDealAmountEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("736CC297"));
    }
    public static com.kingdee.eas.fdc.tenancy.INewDealAmountEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.INewDealAmountEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("736CC297"));
    }
}