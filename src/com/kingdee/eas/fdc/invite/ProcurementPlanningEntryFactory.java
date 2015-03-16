package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProcurementPlanningEntryFactory
{
    private ProcurementPlanningEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IProcurementPlanningEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IProcurementPlanningEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6680B169") ,com.kingdee.eas.fdc.invite.IProcurementPlanningEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IProcurementPlanningEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IProcurementPlanningEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6680B169") ,com.kingdee.eas.fdc.invite.IProcurementPlanningEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IProcurementPlanningEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IProcurementPlanningEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6680B169"));
    }
    public static com.kingdee.eas.fdc.invite.IProcurementPlanningEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IProcurementPlanningEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6680B169"));
    }
}