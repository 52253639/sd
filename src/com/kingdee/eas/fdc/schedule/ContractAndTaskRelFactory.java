package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractAndTaskRelFactory
{
    private ContractAndTaskRelFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IContractAndTaskRel getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IContractAndTaskRel)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("68747005") ,com.kingdee.eas.fdc.schedule.IContractAndTaskRel.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IContractAndTaskRel getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IContractAndTaskRel)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("68747005") ,com.kingdee.eas.fdc.schedule.IContractAndTaskRel.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IContractAndTaskRel getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IContractAndTaskRel)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("68747005"));
    }
    public static com.kingdee.eas.fdc.schedule.IContractAndTaskRel getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IContractAndTaskRel)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("68747005"));
    }
}