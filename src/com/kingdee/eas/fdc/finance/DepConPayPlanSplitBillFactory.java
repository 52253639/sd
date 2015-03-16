package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DepConPayPlanSplitBillFactory
{
    private DepConPayPlanSplitBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IDepConPayPlanSplitBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IDepConPayPlanSplitBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("AF140E32") ,com.kingdee.eas.fdc.finance.IDepConPayPlanSplitBill.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IDepConPayPlanSplitBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IDepConPayPlanSplitBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("AF140E32") ,com.kingdee.eas.fdc.finance.IDepConPayPlanSplitBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IDepConPayPlanSplitBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IDepConPayPlanSplitBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("AF140E32"));
    }
    public static com.kingdee.eas.fdc.finance.IDepConPayPlanSplitBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IDepConPayPlanSplitBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("AF140E32"));
    }
}