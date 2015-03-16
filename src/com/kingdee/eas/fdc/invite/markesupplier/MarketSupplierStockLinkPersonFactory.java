package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierStockLinkPersonFactory
{
    private MarketSupplierStockLinkPersonFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockLinkPerson getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockLinkPerson)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9BB9DD8F") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockLinkPerson.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockLinkPerson getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockLinkPerson)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9BB9DD8F") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockLinkPerson.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockLinkPerson getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockLinkPerson)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9BB9DD8F"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockLinkPerson getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockLinkPerson)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9BB9DD8F"));
    }
}