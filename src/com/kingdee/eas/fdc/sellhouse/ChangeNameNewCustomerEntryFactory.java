package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChangeNameNewCustomerEntryFactory
{
    private ChangeNameNewCustomerEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangeNameNewCustomerEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeNameNewCustomerEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7920110A") ,com.kingdee.eas.fdc.sellhouse.IChangeNameNewCustomerEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IChangeNameNewCustomerEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeNameNewCustomerEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7920110A") ,com.kingdee.eas.fdc.sellhouse.IChangeNameNewCustomerEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangeNameNewCustomerEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeNameNewCustomerEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7920110A"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangeNameNewCustomerEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeNameNewCustomerEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7920110A"));
    }
}