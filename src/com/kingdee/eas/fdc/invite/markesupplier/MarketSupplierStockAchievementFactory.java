package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierStockAchievementFactory
{
    private MarketSupplierStockAchievementFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockAchievement getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockAchievement)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E5C3B98F") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockAchievement.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockAchievement getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockAchievement)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E5C3B98F") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockAchievement.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockAchievement getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockAchievement)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E5C3B98F"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockAchievement getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockAchievement)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E5C3B98F"));
    }
}