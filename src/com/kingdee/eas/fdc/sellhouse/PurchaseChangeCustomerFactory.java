package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PurchaseChangeCustomerFactory
{
    private PurchaseChangeCustomerFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangeCustomer getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangeCustomer)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EB55C3AA") ,com.kingdee.eas.fdc.sellhouse.IPurchaseChangeCustomer.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangeCustomer getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangeCustomer)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EB55C3AA") ,com.kingdee.eas.fdc.sellhouse.IPurchaseChangeCustomer.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangeCustomer getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangeCustomer)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EB55C3AA"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangeCustomer getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangeCustomer)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EB55C3AA"));
    }
}