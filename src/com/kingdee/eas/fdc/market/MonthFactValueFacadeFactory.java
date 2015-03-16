package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MonthFactValueFacadeFactory
{
    private MonthFactValueFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IMonthFactValueFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMonthFactValueFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("70D10410") ,com.kingdee.eas.fdc.market.IMonthFactValueFacade.class);
    }
    
    public static com.kingdee.eas.fdc.market.IMonthFactValueFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMonthFactValueFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("70D10410") ,com.kingdee.eas.fdc.market.IMonthFactValueFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IMonthFactValueFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMonthFactValueFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("70D10410"));
    }
    public static com.kingdee.eas.fdc.market.IMonthFactValueFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMonthFactValueFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("70D10410"));
    }
}