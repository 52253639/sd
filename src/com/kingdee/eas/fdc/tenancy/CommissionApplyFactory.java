package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CommissionApplyFactory
{
    private CommissionApplyFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ICommissionApply getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICommissionApply)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("616C187A") ,com.kingdee.eas.fdc.tenancy.ICommissionApply.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ICommissionApply getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICommissionApply)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("616C187A") ,com.kingdee.eas.fdc.tenancy.ICommissionApply.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ICommissionApply getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICommissionApply)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("616C187A"));
    }
    public static com.kingdee.eas.fdc.tenancy.ICommissionApply getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICommissionApply)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("616C187A"));
    }
}