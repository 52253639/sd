package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PrePurchasePayListEntryFactory
{
    private PrePurchasePayListEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPrePurchasePayListEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPrePurchasePayListEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2DDCF055") ,com.kingdee.eas.fdc.sellhouse.IPrePurchasePayListEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPrePurchasePayListEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPrePurchasePayListEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2DDCF055") ,com.kingdee.eas.fdc.sellhouse.IPrePurchasePayListEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPrePurchasePayListEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPrePurchasePayListEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2DDCF055"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPrePurchasePayListEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPrePurchasePayListEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2DDCF055"));
    }
}