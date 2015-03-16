package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PrePurchaseCustomerEntryFactory
{
    private PrePurchaseCustomerEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPrePurchaseCustomerEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPrePurchaseCustomerEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F473DEAB") ,com.kingdee.eas.fdc.sellhouse.IPrePurchaseCustomerEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPrePurchaseCustomerEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPrePurchaseCustomerEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F473DEAB") ,com.kingdee.eas.fdc.sellhouse.IPrePurchaseCustomerEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPrePurchaseCustomerEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPrePurchaseCustomerEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F473DEAB"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPrePurchaseCustomerEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPrePurchaseCustomerEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F473DEAB"));
    }
}