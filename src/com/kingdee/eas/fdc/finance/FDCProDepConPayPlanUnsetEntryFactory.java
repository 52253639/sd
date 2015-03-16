package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCProDepConPayPlanUnsetEntryFactory
{
    private FDCProDepConPayPlanUnsetEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanUnsetEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanUnsetEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EB629610") ,com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanUnsetEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanUnsetEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanUnsetEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EB629610") ,com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanUnsetEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanUnsetEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanUnsetEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EB629610"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanUnsetEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanUnsetEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EB629610"));
    }
}