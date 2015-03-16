package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CustomerEntryFactory
{
    private CustomerEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ICustomerEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICustomerEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4FDDA839") ,com.kingdee.eas.fdc.sellhouse.ICustomerEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ICustomerEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICustomerEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4FDDA839") ,com.kingdee.eas.fdc.sellhouse.ICustomerEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ICustomerEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICustomerEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4FDDA839"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ICustomerEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICustomerEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4FDDA839"));
    }
}