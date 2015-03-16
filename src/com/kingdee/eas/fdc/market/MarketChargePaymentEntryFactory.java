package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketChargePaymentEntryFactory
{
    private MarketChargePaymentEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IMarketChargePaymentEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketChargePaymentEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DE13BF0D") ,com.kingdee.eas.fdc.market.IMarketChargePaymentEntry.class);
    }
    
    public static com.kingdee.eas.fdc.market.IMarketChargePaymentEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketChargePaymentEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DE13BF0D") ,com.kingdee.eas.fdc.market.IMarketChargePaymentEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IMarketChargePaymentEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketChargePaymentEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DE13BF0D"));
    }
    public static com.kingdee.eas.fdc.market.IMarketChargePaymentEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketChargePaymentEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DE13BF0D"));
    }
}