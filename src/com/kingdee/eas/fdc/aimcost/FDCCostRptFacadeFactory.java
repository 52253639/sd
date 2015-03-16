package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCCostRptFacadeFactory
{
    private FDCCostRptFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IFDCCostRptFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IFDCCostRptFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B118BCD5") ,com.kingdee.eas.fdc.aimcost.IFDCCostRptFacade.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IFDCCostRptFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IFDCCostRptFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B118BCD5") ,com.kingdee.eas.fdc.aimcost.IFDCCostRptFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IFDCCostRptFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IFDCCostRptFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B118BCD5"));
    }
    public static com.kingdee.eas.fdc.aimcost.IFDCCostRptFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IFDCCostRptFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B118BCD5"));
    }
}