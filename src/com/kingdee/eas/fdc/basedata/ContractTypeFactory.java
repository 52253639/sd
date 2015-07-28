package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractTypeFactory
{
    private ContractTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IContractType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IContractType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B371775E") ,com.kingdee.eas.fdc.basedata.IContractType.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IContractType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IContractType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B371775E") ,com.kingdee.eas.fdc.basedata.IContractType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IContractType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IContractType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B371775E"));
    }
    public static com.kingdee.eas.fdc.basedata.IContractType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IContractType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B371775E"));
    }
}