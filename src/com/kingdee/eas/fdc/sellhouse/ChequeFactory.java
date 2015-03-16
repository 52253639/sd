package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChequeFactory
{
    private ChequeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ICheque getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICheque)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5EC688BC") ,com.kingdee.eas.fdc.sellhouse.ICheque.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ICheque getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICheque)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5EC688BC") ,com.kingdee.eas.fdc.sellhouse.ICheque.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ICheque getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICheque)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5EC688BC"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ICheque getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICheque)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5EC688BC"));
    }
}