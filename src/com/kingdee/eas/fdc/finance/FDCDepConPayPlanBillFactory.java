package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCDepConPayPlanBillFactory
{
    private FDCDepConPayPlanBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F288954F") ,com.kingdee.eas.fdc.finance.IFDCDepConPayPlanBill.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F288954F") ,com.kingdee.eas.fdc.finance.IFDCDepConPayPlanBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F288954F"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F288954F"));
    }
}