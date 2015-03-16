package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCCustomerFactory
{
    private FDCCustomerFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IFDCCustomer getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFDCCustomer)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("682588A8") ,com.kingdee.eas.fdc.sellhouse.IFDCCustomer.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IFDCCustomer getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFDCCustomer)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("682588A8") ,com.kingdee.eas.fdc.sellhouse.IFDCCustomer.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IFDCCustomer getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFDCCustomer)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("682588A8"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IFDCCustomer getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFDCCustomer)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("682588A8"));
    }
}