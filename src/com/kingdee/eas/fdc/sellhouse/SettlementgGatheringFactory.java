package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SettlementgGatheringFactory
{
    private SettlementgGatheringFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISettlementgGathering getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISettlementgGathering)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B2E12CA4") ,com.kingdee.eas.fdc.sellhouse.ISettlementgGathering.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISettlementgGathering getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISettlementgGathering)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B2E12CA4") ,com.kingdee.eas.fdc.sellhouse.ISettlementgGathering.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISettlementgGathering getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISettlementgGathering)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B2E12CA4"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISettlementgGathering getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISettlementgGathering)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B2E12CA4"));
    }
}