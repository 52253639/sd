package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCDepConPayPlanEntryFactory
{
    private FDCDepConPayPlanEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5EB6C02A") ,com.kingdee.eas.fdc.finance.IFDCDepConPayPlanEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5EB6C02A") ,com.kingdee.eas.fdc.finance.IFDCDepConPayPlanEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5EB6C02A"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5EB6C02A"));
    }
}