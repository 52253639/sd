package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class LiquidatedFactory
{
    private LiquidatedFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ILiquidated getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ILiquidated)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A0C675B7") ,com.kingdee.eas.fdc.tenancy.ILiquidated.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ILiquidated getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ILiquidated)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A0C675B7") ,com.kingdee.eas.fdc.tenancy.ILiquidated.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ILiquidated getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ILiquidated)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A0C675B7"));
    }
    public static com.kingdee.eas.fdc.tenancy.ILiquidated getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ILiquidated)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A0C675B7"));
    }
}