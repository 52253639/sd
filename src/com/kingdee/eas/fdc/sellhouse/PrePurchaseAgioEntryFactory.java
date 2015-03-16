package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PrePurchaseAgioEntryFactory
{
    private PrePurchaseAgioEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPrePurchaseAgioEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPrePurchaseAgioEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E0CFCEFD") ,com.kingdee.eas.fdc.sellhouse.IPrePurchaseAgioEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPrePurchaseAgioEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPrePurchaseAgioEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E0CFCEFD") ,com.kingdee.eas.fdc.sellhouse.IPrePurchaseAgioEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPrePurchaseAgioEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPrePurchaseAgioEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E0CFCEFD"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPrePurchaseAgioEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPrePurchaseAgioEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E0CFCEFD"));
    }
}