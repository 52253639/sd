package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCDepConPayPlanUnsettledConFactory
{
    private FDCDepConPayPlanUnsettledConFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanUnsettledCon getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanUnsettledCon)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("636FFEEA") ,com.kingdee.eas.fdc.finance.IFDCDepConPayPlanUnsettledCon.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanUnsettledCon getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanUnsettledCon)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("636FFEEA") ,com.kingdee.eas.fdc.finance.IFDCDepConPayPlanUnsettledCon.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanUnsettledCon getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanUnsettledCon)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("636FFEEA"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanUnsettledCon getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanUnsettledCon)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("636FFEEA"));
    }
}