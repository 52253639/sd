package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class LiquidatedManageFactory
{
    private LiquidatedManageFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ILiquidatedManage getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ILiquidatedManage)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("06DE99FC") ,com.kingdee.eas.fdc.tenancy.ILiquidatedManage.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ILiquidatedManage getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ILiquidatedManage)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("06DE99FC") ,com.kingdee.eas.fdc.tenancy.ILiquidatedManage.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ILiquidatedManage getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ILiquidatedManage)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("06DE99FC"));
    }
    public static com.kingdee.eas.fdc.tenancy.ILiquidatedManage getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ILiquidatedManage)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("06DE99FC"));
    }
}