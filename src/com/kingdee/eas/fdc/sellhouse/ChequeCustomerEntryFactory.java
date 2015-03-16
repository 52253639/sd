package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChequeCustomerEntryFactory
{
    private ChequeCustomerEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IChequeCustomerEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChequeCustomerEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B35F8BD8") ,com.kingdee.eas.fdc.sellhouse.IChequeCustomerEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IChequeCustomerEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChequeCustomerEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B35F8BD8") ,com.kingdee.eas.fdc.sellhouse.IChequeCustomerEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IChequeCustomerEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChequeCustomerEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B35F8BD8"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IChequeCustomerEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChequeCustomerEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B35F8BD8"));
    }
}