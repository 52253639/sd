package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CostAcctOrgAssignFactory
{
    private CostAcctOrgAssignFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.ICostAcctOrgAssign getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICostAcctOrgAssign)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5F6C52DC") ,com.kingdee.eas.fdc.aimcost.ICostAcctOrgAssign.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.ICostAcctOrgAssign getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICostAcctOrgAssign)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5F6C52DC") ,com.kingdee.eas.fdc.aimcost.ICostAcctOrgAssign.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.ICostAcctOrgAssign getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICostAcctOrgAssign)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5F6C52DC"));
    }
    public static com.kingdee.eas.fdc.aimcost.ICostAcctOrgAssign getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICostAcctOrgAssign)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5F6C52DC"));
    }
}