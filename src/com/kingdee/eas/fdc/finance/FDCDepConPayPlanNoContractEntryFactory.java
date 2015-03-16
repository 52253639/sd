package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCDepConPayPlanNoContractEntryFactory
{
    private FDCDepConPayPlanNoContractEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanNoContractEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanNoContractEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("207197F7") ,com.kingdee.eas.fdc.finance.IFDCDepConPayPlanNoContractEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanNoContractEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanNoContractEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("207197F7") ,com.kingdee.eas.fdc.finance.IFDCDepConPayPlanNoContractEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanNoContractEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanNoContractEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("207197F7"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanNoContractEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanNoContractEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("207197F7"));
    }
}