package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCProDepConPayPlanFactory
{
    private FDCProDepConPayPlanFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepConPayPlan getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepConPayPlan)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B3BEFFE7") ,com.kingdee.eas.fdc.finance.IFDCProDepConPayPlan.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCProDepConPayPlan getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepConPayPlan)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B3BEFFE7") ,com.kingdee.eas.fdc.finance.IFDCProDepConPayPlan.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepConPayPlan getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepConPayPlan)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B3BEFFE7"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepConPayPlan getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepConPayPlan)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B3BEFFE7"));
    }
}