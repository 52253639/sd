package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractChangeBillFactory
{
    private ContractChangeBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractChangeBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractChangeBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F2141C04") ,com.kingdee.eas.fdc.contract.IContractChangeBill.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractChangeBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractChangeBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F2141C04") ,com.kingdee.eas.fdc.contract.IContractChangeBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractChangeBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractChangeBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F2141C04"));
    }
    public static com.kingdee.eas.fdc.contract.IContractChangeBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractChangeBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F2141C04"));
    }
}