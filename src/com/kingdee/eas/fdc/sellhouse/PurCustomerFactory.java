package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PurCustomerFactory
{
    private PurCustomerFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurCustomer getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurCustomer)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("33B38C50") ,com.kingdee.eas.fdc.sellhouse.IPurCustomer.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPurCustomer getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurCustomer)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("33B38C50") ,com.kingdee.eas.fdc.sellhouse.IPurCustomer.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurCustomer getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurCustomer)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("33B38C50"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurCustomer getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurCustomer)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("33B38C50"));
    }
}