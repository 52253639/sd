package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WorkLoadConfirmBillRelTaskFactory
{
    private WorkLoadConfirmBillRelTaskFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IWorkLoadConfirmBillRelTask getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IWorkLoadConfirmBillRelTask)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7F6F991D") ,com.kingdee.eas.fdc.finance.IWorkLoadConfirmBillRelTask.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IWorkLoadConfirmBillRelTask getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IWorkLoadConfirmBillRelTask)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7F6F991D") ,com.kingdee.eas.fdc.finance.IWorkLoadConfirmBillRelTask.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IWorkLoadConfirmBillRelTask getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IWorkLoadConfirmBillRelTask)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7F6F991D"));
    }
    public static com.kingdee.eas.fdc.finance.IWorkLoadConfirmBillRelTask getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IWorkLoadConfirmBillRelTask)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7F6F991D"));
    }
}