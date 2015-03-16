package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCAdjustBillFactory
{
    private FDCAdjustBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCAdjustBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCAdjustBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D24B04CC") ,com.kingdee.eas.fdc.finance.IFDCAdjustBill.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCAdjustBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCAdjustBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D24B04CC") ,com.kingdee.eas.fdc.finance.IFDCAdjustBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCAdjustBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCAdjustBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D24B04CC"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCAdjustBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCAdjustBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D24B04CC"));
    }
}