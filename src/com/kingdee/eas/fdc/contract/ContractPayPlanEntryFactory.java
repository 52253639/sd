package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractPayPlanEntryFactory
{
    private ContractPayPlanEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractPayPlanEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractPayPlanEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("13073BEE") ,com.kingdee.eas.fdc.contract.IContractPayPlanEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractPayPlanEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractPayPlanEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("13073BEE") ,com.kingdee.eas.fdc.contract.IContractPayPlanEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractPayPlanEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractPayPlanEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("13073BEE"));
    }
    public static com.kingdee.eas.fdc.contract.IContractPayPlanEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractPayPlanEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("13073BEE"));
    }
}