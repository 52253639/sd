package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CostClosePeriodFacadeFactory
{
    private CostClosePeriodFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.ICostClosePeriodFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.ICostClosePeriodFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("99FC09D7") ,com.kingdee.eas.fdc.finance.ICostClosePeriodFacade.class);
    }
    
    public static com.kingdee.eas.fdc.finance.ICostClosePeriodFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.ICostClosePeriodFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("99FC09D7") ,com.kingdee.eas.fdc.finance.ICostClosePeriodFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.ICostClosePeriodFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.ICostClosePeriodFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("99FC09D7"));
    }
    public static com.kingdee.eas.fdc.finance.ICostClosePeriodFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.ICostClosePeriodFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("99FC09D7"));
    }
}