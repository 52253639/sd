package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SincerityPurchaseCustomerEntryFactory
{
    private SincerityPurchaseCustomerEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISincerityPurchaseCustomerEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISincerityPurchaseCustomerEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("282C39B8") ,com.kingdee.eas.fdc.sellhouse.ISincerityPurchaseCustomerEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISincerityPurchaseCustomerEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISincerityPurchaseCustomerEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("282C39B8") ,com.kingdee.eas.fdc.sellhouse.ISincerityPurchaseCustomerEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISincerityPurchaseCustomerEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISincerityPurchaseCustomerEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("282C39B8"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISincerityPurchaseCustomerEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISincerityPurchaseCustomerEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("282C39B8"));
    }
}