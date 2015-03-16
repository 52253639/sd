package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierAppraiseTemplateFactory
{
    private MarketSupplierAppraiseTemplateFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAppraiseTemplate getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAppraiseTemplate)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("05AFBE3A") ,com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAppraiseTemplate.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAppraiseTemplate getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAppraiseTemplate)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("05AFBE3A") ,com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAppraiseTemplate.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAppraiseTemplate getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAppraiseTemplate)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("05AFBE3A"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAppraiseTemplate getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAppraiseTemplate)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("05AFBE3A"));
    }
}