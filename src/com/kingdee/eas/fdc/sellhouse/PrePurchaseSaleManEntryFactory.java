package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PrePurchaseSaleManEntryFactory
{
    private PrePurchaseSaleManEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPrePurchaseSaleManEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPrePurchaseSaleManEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("38D7D248") ,com.kingdee.eas.fdc.sellhouse.IPrePurchaseSaleManEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPrePurchaseSaleManEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPrePurchaseSaleManEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("38D7D248") ,com.kingdee.eas.fdc.sellhouse.IPrePurchaseSaleManEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPrePurchaseSaleManEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPrePurchaseSaleManEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("38D7D248"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPrePurchaseSaleManEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPrePurchaseSaleManEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("38D7D248"));
    }
}