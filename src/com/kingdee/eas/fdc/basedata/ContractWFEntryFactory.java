package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractWFEntryFactory
{
    private ContractWFEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IContractWFEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IContractWFEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4685545F") ,com.kingdee.eas.fdc.basedata.IContractWFEntry.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IContractWFEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IContractWFEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4685545F") ,com.kingdee.eas.fdc.basedata.IContractWFEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IContractWFEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IContractWFEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4685545F"));
    }
    public static com.kingdee.eas.fdc.basedata.IContractWFEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IContractWFEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4685545F"));
    }
}