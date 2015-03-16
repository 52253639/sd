package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AttachRentAdjustEntrysFactory
{
    private AttachRentAdjustEntrysFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IAttachRentAdjustEntrys getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAttachRentAdjustEntrys)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9C40C9D7") ,com.kingdee.eas.fdc.tenancy.IAttachRentAdjustEntrys.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IAttachRentAdjustEntrys getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAttachRentAdjustEntrys)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9C40C9D7") ,com.kingdee.eas.fdc.tenancy.IAttachRentAdjustEntrys.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IAttachRentAdjustEntrys getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAttachRentAdjustEntrys)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9C40C9D7"));
    }
    public static com.kingdee.eas.fdc.tenancy.IAttachRentAdjustEntrys getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAttachRentAdjustEntrys)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9C40C9D7"));
    }
}