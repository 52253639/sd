package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierAppraiseTemplateE1Factory
{
    private MarketSupplierAppraiseTemplateE1Factory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAppraiseTemplateE1 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAppraiseTemplateE1)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("58B92046") ,com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAppraiseTemplateE1.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAppraiseTemplateE1 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAppraiseTemplateE1)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("58B92046") ,com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAppraiseTemplateE1.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAppraiseTemplateE1 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAppraiseTemplateE1)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("58B92046"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAppraiseTemplateE1 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAppraiseTemplateE1)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("58B92046"));
    }
}