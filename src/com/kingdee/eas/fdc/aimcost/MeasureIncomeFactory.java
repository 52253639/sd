package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MeasureIncomeFactory
{
    private MeasureIncomeFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IMeasureIncome getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IMeasureIncome)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C1C610B0") ,com.kingdee.eas.fdc.aimcost.IMeasureIncome.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IMeasureIncome getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IMeasureIncome)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C1C610B0") ,com.kingdee.eas.fdc.aimcost.IMeasureIncome.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IMeasureIncome getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IMeasureIncome)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C1C610B0"));
    }
    public static com.kingdee.eas.fdc.aimcost.IMeasureIncome getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IMeasureIncome)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C1C610B0"));
    }
}