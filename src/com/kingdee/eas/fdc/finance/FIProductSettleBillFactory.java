package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FIProductSettleBillFactory
{
    private FIProductSettleBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFIProductSettleBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFIProductSettleBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2CF92E6F") ,com.kingdee.eas.fdc.finance.IFIProductSettleBill.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFIProductSettleBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFIProductSettleBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2CF92E6F") ,com.kingdee.eas.fdc.finance.IFIProductSettleBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFIProductSettleBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFIProductSettleBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2CF92E6F"));
    }
    public static com.kingdee.eas.fdc.finance.IFIProductSettleBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFIProductSettleBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2CF92E6F"));
    }
}