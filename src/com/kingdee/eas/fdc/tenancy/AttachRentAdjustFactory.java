package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AttachRentAdjustFactory
{
    private AttachRentAdjustFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IAttachRentAdjust getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAttachRentAdjust)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3B7A27F6") ,com.kingdee.eas.fdc.tenancy.IAttachRentAdjust.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IAttachRentAdjust getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAttachRentAdjust)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3B7A27F6") ,com.kingdee.eas.fdc.tenancy.IAttachRentAdjust.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IAttachRentAdjust getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAttachRentAdjust)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3B7A27F6"));
    }
    public static com.kingdee.eas.fdc.tenancy.IAttachRentAdjust getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAttachRentAdjust)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3B7A27F6"));
    }
}