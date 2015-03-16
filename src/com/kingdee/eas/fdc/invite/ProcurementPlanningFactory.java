package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProcurementPlanningFactory
{
    private ProcurementPlanningFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IProcurementPlanning getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IProcurementPlanning)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("898BD489") ,com.kingdee.eas.fdc.invite.IProcurementPlanning.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IProcurementPlanning getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IProcurementPlanning)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("898BD489") ,com.kingdee.eas.fdc.invite.IProcurementPlanning.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IProcurementPlanning getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IProcurementPlanning)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("898BD489"));
    }
    public static com.kingdee.eas.fdc.invite.IProcurementPlanning getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IProcurementPlanning)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("898BD489"));
    }
}