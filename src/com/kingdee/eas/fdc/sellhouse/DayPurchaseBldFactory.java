package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DayPurchaseBldFactory
{
    private DayPurchaseBldFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IDayPurchaseBld getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDayPurchaseBld)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("17CE2AF8") ,com.kingdee.eas.fdc.sellhouse.IDayPurchaseBld.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IDayPurchaseBld getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDayPurchaseBld)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("17CE2AF8") ,com.kingdee.eas.fdc.sellhouse.IDayPurchaseBld.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IDayPurchaseBld getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDayPurchaseBld)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("17CE2AF8"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IDayPurchaseBld getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDayPurchaseBld)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("17CE2AF8"));
    }
}