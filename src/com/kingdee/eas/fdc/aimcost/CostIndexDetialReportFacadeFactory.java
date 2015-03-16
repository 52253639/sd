package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CostIndexDetialReportFacadeFactory
{
    private CostIndexDetialReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.ICostIndexDetialReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICostIndexDetialReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2990191D") ,com.kingdee.eas.fdc.aimcost.ICostIndexDetialReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.ICostIndexDetialReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICostIndexDetialReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2990191D") ,com.kingdee.eas.fdc.aimcost.ICostIndexDetialReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.ICostIndexDetialReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICostIndexDetialReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2990191D"));
    }
    public static com.kingdee.eas.fdc.aimcost.ICostIndexDetialReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICostIndexDetialReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2990191D"));
    }
}