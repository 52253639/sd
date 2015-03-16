package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PriceAdjustEntryFactory
{
    private PriceAdjustEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPriceAdjustEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPriceAdjustEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9E38785A") ,com.kingdee.eas.fdc.sellhouse.IPriceAdjustEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPriceAdjustEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPriceAdjustEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9E38785A") ,com.kingdee.eas.fdc.sellhouse.IPriceAdjustEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPriceAdjustEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPriceAdjustEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9E38785A"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPriceAdjustEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPriceAdjustEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9E38785A"));
    }
}