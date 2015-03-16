package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierAttachListFactory
{
    private MarketSupplierAttachListFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAttachList getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAttachList)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B513C278") ,com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAttachList.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAttachList getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAttachList)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B513C278") ,com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAttachList.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAttachList getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAttachList)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B513C278"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAttachList getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSupplierAttachList)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B513C278"));
    }
}