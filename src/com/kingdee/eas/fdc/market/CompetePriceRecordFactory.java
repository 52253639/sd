package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CompetePriceRecordFactory
{
    private CompetePriceRecordFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.ICompetePriceRecord getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ICompetePriceRecord)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3BD02FE4") ,com.kingdee.eas.fdc.market.ICompetePriceRecord.class);
    }
    
    public static com.kingdee.eas.fdc.market.ICompetePriceRecord getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ICompetePriceRecord)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3BD02FE4") ,com.kingdee.eas.fdc.market.ICompetePriceRecord.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.ICompetePriceRecord getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ICompetePriceRecord)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3BD02FE4"));
    }
    public static com.kingdee.eas.fdc.market.ICompetePriceRecord getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ICompetePriceRecord)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3BD02FE4"));
    }
}