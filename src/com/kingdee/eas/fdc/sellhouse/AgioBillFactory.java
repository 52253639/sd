package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AgioBillFactory
{
    private AgioBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IAgioBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAgioBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C93ED5CE") ,com.kingdee.eas.fdc.sellhouse.IAgioBill.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IAgioBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAgioBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C93ED5CE") ,com.kingdee.eas.fdc.sellhouse.IAgioBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IAgioBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAgioBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C93ED5CE"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IAgioBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAgioBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C93ED5CE"));
    }
}