package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PrePurchseCustomerEntryFactory
{
    private PrePurchseCustomerEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPrePurchseCustomerEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPrePurchseCustomerEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("43F3B998") ,com.kingdee.eas.fdc.sellhouse.IPrePurchseCustomerEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPrePurchseCustomerEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPrePurchseCustomerEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("43F3B998") ,com.kingdee.eas.fdc.sellhouse.IPrePurchseCustomerEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPrePurchseCustomerEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPrePurchseCustomerEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("43F3B998"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPrePurchseCustomerEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPrePurchseCustomerEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("43F3B998"));
    }
}