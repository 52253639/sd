package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TenLiquidatedFactory
{
    private TenLiquidatedFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ITenLiquidated getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenLiquidated)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6375E842") ,com.kingdee.eas.fdc.tenancy.ITenLiquidated.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ITenLiquidated getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenLiquidated)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6375E842") ,com.kingdee.eas.fdc.tenancy.ITenLiquidated.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ITenLiquidated getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenLiquidated)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6375E842"));
    }
    public static com.kingdee.eas.fdc.tenancy.ITenLiquidated getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenLiquidated)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6375E842"));
    }
}