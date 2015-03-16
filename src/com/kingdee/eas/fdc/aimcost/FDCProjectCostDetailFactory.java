package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCProjectCostDetailFactory
{
    private FDCProjectCostDetailFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IFDCProjectCostDetail getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IFDCProjectCostDetail)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("10ACC969") ,com.kingdee.eas.fdc.aimcost.IFDCProjectCostDetail.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IFDCProjectCostDetail getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IFDCProjectCostDetail)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("10ACC969") ,com.kingdee.eas.fdc.aimcost.IFDCProjectCostDetail.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IFDCProjectCostDetail getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IFDCProjectCostDetail)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("10ACC969"));
    }
    public static com.kingdee.eas.fdc.aimcost.IFDCProjectCostDetail getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IFDCProjectCostDetail)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("10ACC969"));
    }
}