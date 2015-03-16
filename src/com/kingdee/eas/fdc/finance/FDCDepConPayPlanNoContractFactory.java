package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCDepConPayPlanNoContractFactory
{
    private FDCDepConPayPlanNoContractFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanNoContract getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanNoContract)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EB0C8D3B") ,com.kingdee.eas.fdc.finance.IFDCDepConPayPlanNoContract.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanNoContract getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanNoContract)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EB0C8D3B") ,com.kingdee.eas.fdc.finance.IFDCDepConPayPlanNoContract.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanNoContract getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanNoContract)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EB0C8D3B"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanNoContract getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanNoContract)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EB0C8D3B"));
    }
}