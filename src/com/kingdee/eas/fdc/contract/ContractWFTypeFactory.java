package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractWFTypeFactory
{
    private ContractWFTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractWFType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractWFType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8B1F0936") ,com.kingdee.eas.fdc.contract.IContractWFType.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractWFType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractWFType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8B1F0936") ,com.kingdee.eas.fdc.contract.IContractWFType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractWFType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractWFType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8B1F0936"));
    }
    public static com.kingdee.eas.fdc.contract.IContractWFType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractWFType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8B1F0936"));
    }
}