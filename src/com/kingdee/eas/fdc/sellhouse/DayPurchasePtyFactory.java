package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DayPurchasePtyFactory
{
    private DayPurchasePtyFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IDayPurchasePty getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDayPurchasePty)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("17CE6093") ,com.kingdee.eas.fdc.sellhouse.IDayPurchasePty.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IDayPurchasePty getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDayPurchasePty)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("17CE6093") ,com.kingdee.eas.fdc.sellhouse.IDayPurchasePty.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IDayPurchasePty getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDayPurchasePty)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("17CE6093"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IDayPurchasePty getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDayPurchasePty)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("17CE6093"));
    }
}