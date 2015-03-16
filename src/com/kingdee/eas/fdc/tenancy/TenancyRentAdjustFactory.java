package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TenancyRentAdjustFactory
{
    private TenancyRentAdjustFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyRentAdjust getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyRentAdjust)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2C5E6CBF") ,com.kingdee.eas.fdc.tenancy.ITenancyRentAdjust.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ITenancyRentAdjust getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyRentAdjust)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2C5E6CBF") ,com.kingdee.eas.fdc.tenancy.ITenancyRentAdjust.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyRentAdjust getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyRentAdjust)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2C5E6CBF"));
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyRentAdjust getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyRentAdjust)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2C5E6CBF"));
    }
}