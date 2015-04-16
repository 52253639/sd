package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractBillFactory
{
    private ContractBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0D6DD1F4") ,com.kingdee.eas.fdc.contract.IContractBill.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0D6DD1F4") ,com.kingdee.eas.fdc.contract.IContractBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0D6DD1F4"));
    }
    public static com.kingdee.eas.fdc.contract.IContractBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0D6DD1F4"));
    }
}