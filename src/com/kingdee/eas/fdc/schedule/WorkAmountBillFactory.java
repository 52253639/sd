package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WorkAmountBillFactory
{
    private WorkAmountBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IWorkAmountBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IWorkAmountBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("75D27BC6") ,com.kingdee.eas.fdc.schedule.IWorkAmountBill.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IWorkAmountBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IWorkAmountBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("75D27BC6") ,com.kingdee.eas.fdc.schedule.IWorkAmountBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IWorkAmountBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IWorkAmountBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("75D27BC6"));
    }
    public static com.kingdee.eas.fdc.schedule.IWorkAmountBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IWorkAmountBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("75D27BC6"));
    }
}