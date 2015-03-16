package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class QuaLevelFactory
{
    private QuaLevelFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.IQuaLevel getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IQuaLevel)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E75504FF") ,com.kingdee.eas.fdc.invite.supplier.IQuaLevel.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.IQuaLevel getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IQuaLevel)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E75504FF") ,com.kingdee.eas.fdc.invite.supplier.IQuaLevel.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.IQuaLevel getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IQuaLevel)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E75504FF"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.IQuaLevel getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IQuaLevel)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E75504FF"));
    }
}