package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCProDepConPayPlanContractFactory
{
    private FDCProDepConPayPlanContractFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanContract getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanContract)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EC1F9E19") ,com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanContract.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanContract getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanContract)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EC1F9E19") ,com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanContract.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanContract getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanContract)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EC1F9E19"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanContract getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanContract)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EC1F9E19"));
    }
}