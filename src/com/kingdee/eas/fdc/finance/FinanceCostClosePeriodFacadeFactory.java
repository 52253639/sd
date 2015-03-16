package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FinanceCostClosePeriodFacadeFactory
{
    private FinanceCostClosePeriodFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFinanceCostClosePeriodFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFinanceCostClosePeriodFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F4FE345B") ,com.kingdee.eas.fdc.finance.IFinanceCostClosePeriodFacade.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFinanceCostClosePeriodFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFinanceCostClosePeriodFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F4FE345B") ,com.kingdee.eas.fdc.finance.IFinanceCostClosePeriodFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFinanceCostClosePeriodFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFinanceCostClosePeriodFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F4FE345B"));
    }
    public static com.kingdee.eas.fdc.finance.IFinanceCostClosePeriodFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFinanceCostClosePeriodFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F4FE345B"));
    }
}