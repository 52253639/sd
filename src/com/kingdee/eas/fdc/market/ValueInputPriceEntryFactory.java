package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ValueInputPriceEntryFactory
{
    private ValueInputPriceEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IValueInputPriceEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IValueInputPriceEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C1BC95B3") ,com.kingdee.eas.fdc.market.IValueInputPriceEntry.class);
    }
    
    public static com.kingdee.eas.fdc.market.IValueInputPriceEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IValueInputPriceEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C1BC95B3") ,com.kingdee.eas.fdc.market.IValueInputPriceEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IValueInputPriceEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IValueInputPriceEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C1BC95B3"));
    }
    public static com.kingdee.eas.fdc.market.IValueInputPriceEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IValueInputPriceEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C1BC95B3"));
    }
}