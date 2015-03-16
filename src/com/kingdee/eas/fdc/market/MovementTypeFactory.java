package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MovementTypeFactory
{
    private MovementTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IMovementType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMovementType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3A1648DA") ,com.kingdee.eas.fdc.market.IMovementType.class);
    }
    
    public static com.kingdee.eas.fdc.market.IMovementType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMovementType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3A1648DA") ,com.kingdee.eas.fdc.market.IMovementType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IMovementType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMovementType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3A1648DA"));
    }
    public static com.kingdee.eas.fdc.market.IMovementType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMovementType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3A1648DA"));
    }
}