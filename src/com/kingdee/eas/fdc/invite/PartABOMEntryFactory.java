package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PartABOMEntryFactory
{
    private PartABOMEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IPartABOMEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IPartABOMEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("65505EBC") ,com.kingdee.eas.fdc.invite.IPartABOMEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IPartABOMEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IPartABOMEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("65505EBC") ,com.kingdee.eas.fdc.invite.IPartABOMEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IPartABOMEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IPartABOMEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("65505EBC"));
    }
    public static com.kingdee.eas.fdc.invite.IPartABOMEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IPartABOMEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("65505EBC"));
    }
}