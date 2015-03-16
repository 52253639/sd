package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChangeAuditBillFactory
{
    private ChangeAuditBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IChangeAuditBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IChangeAuditBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("70116117") ,com.kingdee.eas.fdc.contract.IChangeAuditBill.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IChangeAuditBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IChangeAuditBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("70116117") ,com.kingdee.eas.fdc.contract.IChangeAuditBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IChangeAuditBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IChangeAuditBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("70116117"));
    }
    public static com.kingdee.eas.fdc.contract.IChangeAuditBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IChangeAuditBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("70116117"));
    }
}