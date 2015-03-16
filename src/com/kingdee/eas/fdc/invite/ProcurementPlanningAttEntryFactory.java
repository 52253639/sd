package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProcurementPlanningAttEntryFactory
{
    private ProcurementPlanningAttEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IProcurementPlanningAttEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IProcurementPlanningAttEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F588B9FA") ,com.kingdee.eas.fdc.invite.IProcurementPlanningAttEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IProcurementPlanningAttEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IProcurementPlanningAttEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F588B9FA") ,com.kingdee.eas.fdc.invite.IProcurementPlanningAttEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IProcurementPlanningAttEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IProcurementPlanningAttEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F588B9FA"));
    }
    public static com.kingdee.eas.fdc.invite.IProcurementPlanningAttEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IProcurementPlanningAttEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F588B9FA"));
    }
}