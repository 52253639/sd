package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCDepConPayPlanItemFactory
{
    private FDCDepConPayPlanItemFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanItem getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanItem)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F28BEC5B") ,com.kingdee.eas.fdc.finance.IFDCDepConPayPlanItem.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanItem getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanItem)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F28BEC5B") ,com.kingdee.eas.fdc.finance.IFDCDepConPayPlanItem.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanItem getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanItem)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F28BEC5B"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanItem getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanItem)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F28BEC5B"));
    }
}