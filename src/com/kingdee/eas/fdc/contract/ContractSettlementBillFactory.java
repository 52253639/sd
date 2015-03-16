package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractSettlementBillFactory
{
    private ContractSettlementBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractSettlementBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractSettlementBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1D604E7D") ,com.kingdee.eas.fdc.contract.IContractSettlementBill.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractSettlementBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractSettlementBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1D604E7D") ,com.kingdee.eas.fdc.contract.IContractSettlementBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractSettlementBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractSettlementBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1D604E7D"));
    }
    public static com.kingdee.eas.fdc.contract.IContractSettlementBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractSettlementBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1D604E7D"));
    }
}