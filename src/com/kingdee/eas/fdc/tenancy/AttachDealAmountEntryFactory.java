package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AttachDealAmountEntryFactory
{
    private AttachDealAmountEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IAttachDealAmountEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAttachDealAmountEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("44C627E0") ,com.kingdee.eas.fdc.tenancy.IAttachDealAmountEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IAttachDealAmountEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAttachDealAmountEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("44C627E0") ,com.kingdee.eas.fdc.tenancy.IAttachDealAmountEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IAttachDealAmountEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAttachDealAmountEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("44C627E0"));
    }
    public static com.kingdee.eas.fdc.tenancy.IAttachDealAmountEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAttachDealAmountEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("44C627E0"));
    }
}