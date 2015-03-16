package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractPayPlanNewFactory
{
    private ContractPayPlanNewFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IContractPayPlanNew getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IContractPayPlanNew)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("736E01F0") ,com.kingdee.eas.fdc.finance.IContractPayPlanNew.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IContractPayPlanNew getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IContractPayPlanNew)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("736E01F0") ,com.kingdee.eas.fdc.finance.IContractPayPlanNew.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IContractPayPlanNew getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IContractPayPlanNew)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("736E01F0"));
    }
    public static com.kingdee.eas.fdc.finance.IContractPayPlanNew getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IContractPayPlanNew)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("736E01F0"));
    }
}