package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SaleBalanceFactory
{
    private SaleBalanceFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISaleBalance getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISaleBalance)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("85E60ADA") ,com.kingdee.eas.fdc.sellhouse.ISaleBalance.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISaleBalance getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISaleBalance)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("85E60ADA") ,com.kingdee.eas.fdc.sellhouse.ISaleBalance.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISaleBalance getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISaleBalance)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("85E60ADA"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISaleBalance getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISaleBalance)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("85E60ADA"));
    }
}