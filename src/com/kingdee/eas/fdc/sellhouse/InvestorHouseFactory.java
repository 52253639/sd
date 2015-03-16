package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InvestorHouseFactory
{
    private InvestorHouseFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IInvestorHouse getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IInvestorHouse)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("58D7C66D") ,com.kingdee.eas.fdc.sellhouse.IInvestorHouse.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IInvestorHouse getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IInvestorHouse)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("58D7C66D") ,com.kingdee.eas.fdc.sellhouse.IInvestorHouse.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IInvestorHouse getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IInvestorHouse)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("58D7C66D"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IInvestorHouse getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IInvestorHouse)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("58D7C66D"));
    }
}