package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierFileTypFactory
{
    private MarketSupplierFileTypFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierFileTyp getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierFileTyp)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("78B4859A") ,com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierFileTyp.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierFileTyp getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierFileTyp)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("78B4859A") ,com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierFileTyp.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierFileTyp getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierFileTyp)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("78B4859A"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierFileTyp getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierFileTyp)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("78B4859A"));
    }
}