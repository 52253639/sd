package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OldAttachDealAmountEntryFactory
{
    private OldAttachDealAmountEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IOldAttachDealAmountEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IOldAttachDealAmountEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("71EA5CAB") ,com.kingdee.eas.fdc.tenancy.IOldAttachDealAmountEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IOldAttachDealAmountEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IOldAttachDealAmountEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("71EA5CAB") ,com.kingdee.eas.fdc.tenancy.IOldAttachDealAmountEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IOldAttachDealAmountEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IOldAttachDealAmountEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("71EA5CAB"));
    }
    public static com.kingdee.eas.fdc.tenancy.IOldAttachDealAmountEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IOldAttachDealAmountEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("71EA5CAB"));
    }
}