package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCSplKeepContractAuditBillFactory
{
    private FDCSplKeepContractAuditBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplKeepContractAuditBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplKeepContractAuditBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FC287569") ,com.kingdee.eas.fdc.invite.supplier.IFDCSplKeepContractAuditBill.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplKeepContractAuditBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplKeepContractAuditBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FC287569") ,com.kingdee.eas.fdc.invite.supplier.IFDCSplKeepContractAuditBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplKeepContractAuditBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplKeepContractAuditBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FC287569"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplKeepContractAuditBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplKeepContractAuditBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FC287569"));
    }
}