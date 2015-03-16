package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BatchSettlementFacadeFactory
{
    private BatchSettlementFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IBatchSettlementFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBatchSettlementFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0E7E4A82") ,com.kingdee.eas.fdc.sellhouse.IBatchSettlementFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IBatchSettlementFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBatchSettlementFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0E7E4A82") ,com.kingdee.eas.fdc.sellhouse.IBatchSettlementFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IBatchSettlementFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBatchSettlementFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0E7E4A82"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IBatchSettlementFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBatchSettlementFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0E7E4A82"));
    }
}