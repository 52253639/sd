package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCProDepConPayPlanNoContractFactory
{
    private FDCProDepConPayPlanNoContractFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanNoContract getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanNoContract)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("60C5993A") ,com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanNoContract.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanNoContract getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanNoContract)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("60C5993A") ,com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanNoContract.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanNoContract getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanNoContract)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("60C5993A"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanNoContract getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanNoContract)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("60C5993A"));
    }
}