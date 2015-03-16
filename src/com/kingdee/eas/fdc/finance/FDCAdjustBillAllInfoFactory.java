package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCAdjustBillAllInfoFactory
{
    private FDCAdjustBillAllInfoFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCAdjustBillAllInfo getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCAdjustBillAllInfo)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8C4D6F83") ,com.kingdee.eas.fdc.finance.IFDCAdjustBillAllInfo.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCAdjustBillAllInfo getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCAdjustBillAllInfo)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8C4D6F83") ,com.kingdee.eas.fdc.finance.IFDCAdjustBillAllInfo.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCAdjustBillAllInfo getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCAdjustBillAllInfo)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8C4D6F83"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCAdjustBillAllInfo getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCAdjustBillAllInfo)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8C4D6F83"));
    }
}