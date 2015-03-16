package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ReceptionTypeFactory
{
    private ReceptionTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IReceptionType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IReceptionType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6BF4F7AE") ,com.kingdee.eas.fdc.sellhouse.IReceptionType.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IReceptionType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IReceptionType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6BF4F7AE") ,com.kingdee.eas.fdc.sellhouse.IReceptionType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IReceptionType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IReceptionType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6BF4F7AE"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IReceptionType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IReceptionType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6BF4F7AE"));
    }
}