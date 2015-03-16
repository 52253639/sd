package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCDepConPayPlanFacadeFactory
{
    private FDCDepConPayPlanFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7916EFE2") ,com.kingdee.eas.fdc.finance.IFDCDepConPayPlanFacade.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7916EFE2") ,com.kingdee.eas.fdc.finance.IFDCDepConPayPlanFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7916EFE2"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7916EFE2"));
    }
}