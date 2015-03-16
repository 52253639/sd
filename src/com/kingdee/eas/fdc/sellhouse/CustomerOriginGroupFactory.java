package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CustomerOriginGroupFactory
{
    private CustomerOriginGroupFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ICustomerOriginGroup getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICustomerOriginGroup)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("52294D80") ,com.kingdee.eas.fdc.sellhouse.ICustomerOriginGroup.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ICustomerOriginGroup getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICustomerOriginGroup)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("52294D80") ,com.kingdee.eas.fdc.sellhouse.ICustomerOriginGroup.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ICustomerOriginGroup getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICustomerOriginGroup)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("52294D80"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ICustomerOriginGroup getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICustomerOriginGroup)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("52294D80"));
    }
}