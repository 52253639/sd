package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCProDepConPayPlanNoContractEntryFactory
{
    private FDCProDepConPayPlanNoContractEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanNoContractEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanNoContractEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("CECB3358") ,com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanNoContractEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanNoContractEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanNoContractEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("CECB3358") ,com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanNoContractEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanNoContractEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanNoContractEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("CECB3358"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanNoContractEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanNoContractEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("CECB3358"));
    }
}