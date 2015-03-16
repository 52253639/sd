package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PriceSetSchemeFactory
{
    private PriceSetSchemeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPriceSetScheme getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPriceSetScheme)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0FAF28F9") ,com.kingdee.eas.fdc.sellhouse.IPriceSetScheme.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPriceSetScheme getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPriceSetScheme)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0FAF28F9") ,com.kingdee.eas.fdc.sellhouse.IPriceSetScheme.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPriceSetScheme getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPriceSetScheme)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0FAF28F9"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPriceSetScheme getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPriceSetScheme)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0FAF28F9"));
    }
}