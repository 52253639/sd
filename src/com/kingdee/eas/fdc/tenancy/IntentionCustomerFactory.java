package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class IntentionCustomerFactory
{
    private IntentionCustomerFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IIntentionCustomer getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IIntentionCustomer)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("75527961") ,com.kingdee.eas.fdc.tenancy.IIntentionCustomer.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IIntentionCustomer getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IIntentionCustomer)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("75527961") ,com.kingdee.eas.fdc.tenancy.IIntentionCustomer.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IIntentionCustomer getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IIntentionCustomer)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("75527961"));
    }
    public static com.kingdee.eas.fdc.tenancy.IIntentionCustomer getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IIntentionCustomer)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("75527961"));
    }
}