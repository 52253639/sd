package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCDepConPayPlanContractFactory
{
    private FDCDepConPayPlanContractFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanContract getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanContract)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4FE59E5A") ,com.kingdee.eas.fdc.finance.IFDCDepConPayPlanContract.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanContract getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanContract)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4FE59E5A") ,com.kingdee.eas.fdc.finance.IFDCDepConPayPlanContract.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanContract getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanContract)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4FE59E5A"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanContract getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanContract)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4FE59E5A"));
    }
}