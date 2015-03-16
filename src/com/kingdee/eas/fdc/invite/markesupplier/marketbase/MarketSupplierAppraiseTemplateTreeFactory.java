package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierAppraiseTemplateTreeFactory
{
    private MarketSupplierAppraiseTemplateTreeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAppraiseTemplateTree getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAppraiseTemplateTree)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0EF9F8F8") ,com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAppraiseTemplateTree.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAppraiseTemplateTree getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAppraiseTemplateTree)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0EF9F8F8") ,com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAppraiseTemplateTree.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAppraiseTemplateTree getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAppraiseTemplateTree)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0EF9F8F8"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAppraiseTemplateTree getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAppraiseTemplateTree)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0EF9F8F8"));
    }
}