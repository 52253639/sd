package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AttachResTypeFactory
{
    private AttachResTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IAttachResType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAttachResType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("488276EC") ,com.kingdee.eas.fdc.tenancy.IAttachResType.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IAttachResType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAttachResType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("488276EC") ,com.kingdee.eas.fdc.tenancy.IAttachResType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IAttachResType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAttachResType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("488276EC"));
    }
    public static com.kingdee.eas.fdc.tenancy.IAttachResType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAttachResType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("488276EC"));
    }
}