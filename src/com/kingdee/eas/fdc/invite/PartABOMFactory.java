package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PartABOMFactory
{
    private PartABOMFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IPartABOM getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IPartABOM)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C403DF56") ,com.kingdee.eas.fdc.invite.IPartABOM.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IPartABOM getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IPartABOM)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C403DF56") ,com.kingdee.eas.fdc.invite.IPartABOM.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IPartABOM getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IPartABOM)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C403DF56"));
    }
    public static com.kingdee.eas.fdc.invite.IPartABOM getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IPartABOM)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C403DF56"));
    }
}