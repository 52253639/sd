package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CustomerWorkAreaEntryFactory
{
    private CustomerWorkAreaEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ICustomerWorkAreaEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICustomerWorkAreaEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5FB83C9B") ,com.kingdee.eas.fdc.sellhouse.ICustomerWorkAreaEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ICustomerWorkAreaEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICustomerWorkAreaEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5FB83C9B") ,com.kingdee.eas.fdc.sellhouse.ICustomerWorkAreaEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ICustomerWorkAreaEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICustomerWorkAreaEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5FB83C9B"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ICustomerWorkAreaEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICustomerWorkAreaEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5FB83C9B"));
    }
}