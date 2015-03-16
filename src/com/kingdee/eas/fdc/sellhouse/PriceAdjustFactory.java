package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PriceAdjustFactory
{
    private PriceAdjustFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPriceAdjust getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPriceAdjust)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1F8B4222") ,com.kingdee.eas.fdc.sellhouse.IPriceAdjust.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPriceAdjust getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPriceAdjust)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1F8B4222") ,com.kingdee.eas.fdc.sellhouse.IPriceAdjust.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPriceAdjust getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPriceAdjust)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1F8B4222"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPriceAdjust getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPriceAdjust)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1F8B4222"));
    }
}