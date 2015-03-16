package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractChangeSettleBillFactory
{
    private ContractChangeSettleBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractChangeSettleBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractChangeSettleBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A12C522F") ,com.kingdee.eas.fdc.contract.IContractChangeSettleBill.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractChangeSettleBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractChangeSettleBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A12C522F") ,com.kingdee.eas.fdc.contract.IContractChangeSettleBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractChangeSettleBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractChangeSettleBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A12C522F"));
    }
    public static com.kingdee.eas.fdc.contract.IContractChangeSettleBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractChangeSettleBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A12C522F"));
    }
}