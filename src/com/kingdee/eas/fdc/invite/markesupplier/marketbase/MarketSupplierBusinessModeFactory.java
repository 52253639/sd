package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierBusinessModeFactory
{
    private MarketSupplierBusinessModeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierBusinessMode getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierBusinessMode)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("24A03E38") ,com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierBusinessMode.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierBusinessMode getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierBusinessMode)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("24A03E38") ,com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierBusinessMode.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierBusinessMode getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierBusinessMode)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("24A03E38"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierBusinessMode getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierBusinessMode)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("24A03E38"));
    }
}