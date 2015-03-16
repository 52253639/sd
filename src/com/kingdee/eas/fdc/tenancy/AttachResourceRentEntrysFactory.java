package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AttachResourceRentEntrysFactory
{
    private AttachResourceRentEntrysFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IAttachResourceRentEntrys getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAttachResourceRentEntrys)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0063AF56") ,com.kingdee.eas.fdc.tenancy.IAttachResourceRentEntrys.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IAttachResourceRentEntrys getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAttachResourceRentEntrys)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0063AF56") ,com.kingdee.eas.fdc.tenancy.IAttachResourceRentEntrys.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IAttachResourceRentEntrys getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAttachResourceRentEntrys)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0063AF56"));
    }
    public static com.kingdee.eas.fdc.tenancy.IAttachResourceRentEntrys getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAttachResourceRentEntrys)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0063AF56"));
    }
}