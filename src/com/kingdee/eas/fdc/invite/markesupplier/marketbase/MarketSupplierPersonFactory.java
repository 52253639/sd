package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierPersonFactory
{
    private MarketSupplierPersonFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierPerson getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierPerson)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("464C37CA") ,com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierPerson.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierPerson getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierPerson)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("464C37CA") ,com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierPerson.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierPerson getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierPerson)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("464C37CA"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierPerson getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierPerson)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("464C37CA"));
    }
}