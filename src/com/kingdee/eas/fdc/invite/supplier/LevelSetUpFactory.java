package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class LevelSetUpFactory
{
    private LevelSetUpFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.ILevelSetUp getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ILevelSetUp)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("166AF1B1") ,com.kingdee.eas.fdc.invite.supplier.ILevelSetUp.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.ILevelSetUp getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ILevelSetUp)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("166AF1B1") ,com.kingdee.eas.fdc.invite.supplier.ILevelSetUp.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.ILevelSetUp getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ILevelSetUp)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("166AF1B1"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.ILevelSetUp getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ILevelSetUp)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("166AF1B1"));
    }
}