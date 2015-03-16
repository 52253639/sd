package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CustomerOriginFactory
{
    private CustomerOriginFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ICustomerOrigin getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICustomerOrigin)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BD1B083F") ,com.kingdee.eas.fdc.sellhouse.ICustomerOrigin.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ICustomerOrigin getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICustomerOrigin)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BD1B083F") ,com.kingdee.eas.fdc.sellhouse.ICustomerOrigin.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ICustomerOrigin getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICustomerOrigin)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BD1B083F"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ICustomerOrigin getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICustomerOrigin)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BD1B083F"));
    }
}