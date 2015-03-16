package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCDepConPayPlanContractEntryFactory
{
    private FDCDepConPayPlanContractEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanContractEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanContractEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("12816238") ,com.kingdee.eas.fdc.finance.IFDCDepConPayPlanContractEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanContractEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanContractEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("12816238") ,com.kingdee.eas.fdc.finance.IFDCDepConPayPlanContractEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanContractEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanContractEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("12816238"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanContractEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanContractEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("12816238"));
    }
}