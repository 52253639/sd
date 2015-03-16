package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCProDepConPayPlanUnsettledFactory
{
    private FDCProDepConPayPlanUnsettledFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanUnsettled getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanUnsettled)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8BCE2F99") ,com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanUnsettled.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanUnsettled getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanUnsettled)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8BCE2F99") ,com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanUnsettled.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanUnsettled getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanUnsettled)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8BCE2F99"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanUnsettled getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanUnsettled)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8BCE2F99"));
    }
}