package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SHECustomerFactory
{
    private SHECustomerFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHECustomer getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHECustomer)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F1713EF3") ,com.kingdee.eas.fdc.sellhouse.ISHECustomer.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISHECustomer getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHECustomer)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F1713EF3") ,com.kingdee.eas.fdc.sellhouse.ISHECustomer.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHECustomer getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHECustomer)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F1713EF3"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHECustomer getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHECustomer)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F1713EF3"));
    }
}