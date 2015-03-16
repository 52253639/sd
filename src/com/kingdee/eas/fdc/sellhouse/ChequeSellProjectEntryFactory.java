package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChequeSellProjectEntryFactory
{
    private ChequeSellProjectEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IChequeSellProjectEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChequeSellProjectEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4C846227") ,com.kingdee.eas.fdc.sellhouse.IChequeSellProjectEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IChequeSellProjectEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChequeSellProjectEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4C846227") ,com.kingdee.eas.fdc.sellhouse.IChequeSellProjectEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IChequeSellProjectEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChequeSellProjectEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4C846227"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IChequeSellProjectEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChequeSellProjectEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4C846227"));
    }
}