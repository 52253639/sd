package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractWithoutTextFactory
{
    private ContractWithoutTextFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractWithoutText getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractWithoutText)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3D9A5388") ,com.kingdee.eas.fdc.contract.IContractWithoutText.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractWithoutText getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractWithoutText)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3D9A5388") ,com.kingdee.eas.fdc.contract.IContractWithoutText.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractWithoutText getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractWithoutText)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3D9A5388"));
    }
    public static com.kingdee.eas.fdc.contract.IContractWithoutText getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractWithoutText)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3D9A5388"));
    }
}