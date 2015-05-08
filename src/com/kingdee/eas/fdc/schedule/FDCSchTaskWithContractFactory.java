package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCSchTaskWithContractFactory
{
    private FDCSchTaskWithContractFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IFDCSchTaskWithContract getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCSchTaskWithContract)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("CAF0B606") ,com.kingdee.eas.fdc.schedule.IFDCSchTaskWithContract.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IFDCSchTaskWithContract getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCSchTaskWithContract)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("CAF0B606") ,com.kingdee.eas.fdc.schedule.IFDCSchTaskWithContract.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IFDCSchTaskWithContract getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCSchTaskWithContract)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("CAF0B606"));
    }
    public static com.kingdee.eas.fdc.schedule.IFDCSchTaskWithContract getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCSchTaskWithContract)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("CAF0B606"));
    }
}