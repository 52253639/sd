package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class NewAttachDealAmountEntryFactory
{
    private NewAttachDealAmountEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.INewAttachDealAmountEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.INewAttachDealAmountEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6A7A76D2") ,com.kingdee.eas.fdc.tenancy.INewAttachDealAmountEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.INewAttachDealAmountEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.INewAttachDealAmountEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6A7A76D2") ,com.kingdee.eas.fdc.tenancy.INewAttachDealAmountEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.INewAttachDealAmountEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.INewAttachDealAmountEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6A7A76D2"));
    }
    public static com.kingdee.eas.fdc.tenancy.INewAttachDealAmountEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.INewAttachDealAmountEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6A7A76D2"));
    }
}