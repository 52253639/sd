package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractPayPlanNewPlanMingxiFactory
{
    private ContractPayPlanNewPlanMingxiFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IContractPayPlanNewPlanMingxi getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IContractPayPlanNewPlanMingxi)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("817A589F") ,com.kingdee.eas.fdc.finance.IContractPayPlanNewPlanMingxi.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IContractPayPlanNewPlanMingxi getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IContractPayPlanNewPlanMingxi)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("817A589F") ,com.kingdee.eas.fdc.finance.IContractPayPlanNewPlanMingxi.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IContractPayPlanNewPlanMingxi getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IContractPayPlanNewPlanMingxi)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("817A589F"));
    }
    public static com.kingdee.eas.fdc.finance.IContractPayPlanNewPlanMingxi getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IContractPayPlanNewPlanMingxi)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("817A589F"));
    }
}