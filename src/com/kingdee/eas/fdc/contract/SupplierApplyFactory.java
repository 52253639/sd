package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierApplyFactory
{
    private SupplierApplyFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.ISupplierApply getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ISupplierApply)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3B110EA7") ,com.kingdee.eas.fdc.contract.ISupplierApply.class);
    }
    
    public static com.kingdee.eas.fdc.contract.ISupplierApply getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ISupplierApply)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3B110EA7") ,com.kingdee.eas.fdc.contract.ISupplierApply.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.ISupplierApply getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ISupplierApply)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3B110EA7"));
    }
    public static com.kingdee.eas.fdc.contract.ISupplierApply getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ISupplierApply)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3B110EA7"));
    }
}