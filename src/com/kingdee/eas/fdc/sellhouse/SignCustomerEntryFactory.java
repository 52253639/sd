package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SignCustomerEntryFactory
{
    private SignCustomerEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISignCustomerEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISignCustomerEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6D87EAFC") ,com.kingdee.eas.fdc.sellhouse.ISignCustomerEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISignCustomerEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISignCustomerEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6D87EAFC") ,com.kingdee.eas.fdc.sellhouse.ISignCustomerEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISignCustomerEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISignCustomerEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6D87EAFC"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISignCustomerEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISignCustomerEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6D87EAFC"));
    }
}