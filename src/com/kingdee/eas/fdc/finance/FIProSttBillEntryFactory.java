package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FIProSttBillEntryFactory
{
    private FIProSttBillEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFIProSttBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFIProSttBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("949642B3") ,com.kingdee.eas.fdc.finance.IFIProSttBillEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFIProSttBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFIProSttBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("949642B3") ,com.kingdee.eas.fdc.finance.IFIProSttBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFIProSttBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFIProSttBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("949642B3"));
    }
    public static com.kingdee.eas.fdc.finance.IFIProSttBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFIProSttBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("949642B3"));
    }
}