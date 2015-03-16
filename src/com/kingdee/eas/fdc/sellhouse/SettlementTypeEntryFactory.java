package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SettlementTypeEntryFactory
{
    private SettlementTypeEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISettlementTypeEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISettlementTypeEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9F3C0F74") ,com.kingdee.eas.fdc.sellhouse.ISettlementTypeEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISettlementTypeEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISettlementTypeEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9F3C0F74") ,com.kingdee.eas.fdc.sellhouse.ISettlementTypeEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISettlementTypeEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISettlementTypeEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9F3C0F74"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISettlementTypeEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISettlementTypeEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9F3C0F74"));
    }
}