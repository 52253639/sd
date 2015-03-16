package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierEvaluationContractEntryFactory
{
    private SupplierEvaluationContractEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierEvaluationContractEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierEvaluationContractEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A733E400") ,com.kingdee.eas.fdc.invite.supplier.ISupplierEvaluationContractEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierEvaluationContractEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierEvaluationContractEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A733E400") ,com.kingdee.eas.fdc.invite.supplier.ISupplierEvaluationContractEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierEvaluationContractEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierEvaluationContractEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A733E400"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierEvaluationContractEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierEvaluationContractEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A733E400"));
    }
}