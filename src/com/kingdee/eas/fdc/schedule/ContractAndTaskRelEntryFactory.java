package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractAndTaskRelEntryFactory
{
    private ContractAndTaskRelEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IContractAndTaskRelEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IContractAndTaskRelEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EB59E36D") ,com.kingdee.eas.fdc.schedule.IContractAndTaskRelEntry.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IContractAndTaskRelEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IContractAndTaskRelEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EB59E36D") ,com.kingdee.eas.fdc.schedule.IContractAndTaskRelEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IContractAndTaskRelEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IContractAndTaskRelEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EB59E36D"));
    }
    public static com.kingdee.eas.fdc.schedule.IContractAndTaskRelEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IContractAndTaskRelEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EB59E36D"));
    }
}