package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InvestorHouseSourceFactory
{
    private InvestorHouseSourceFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IInvestorHouseSource getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IInvestorHouseSource)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4767D4E8") ,com.kingdee.eas.fdc.sellhouse.IInvestorHouseSource.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IInvestorHouseSource getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IInvestorHouseSource)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4767D4E8") ,com.kingdee.eas.fdc.sellhouse.IInvestorHouseSource.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IInvestorHouseSource getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IInvestorHouseSource)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4767D4E8"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IInvestorHouseSource getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IInvestorHouseSource)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4767D4E8"));
    }
}