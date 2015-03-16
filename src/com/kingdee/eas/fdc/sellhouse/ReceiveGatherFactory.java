package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ReceiveGatherFactory
{
    private ReceiveGatherFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IReceiveGather getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IReceiveGather)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("15549BC3") ,com.kingdee.eas.fdc.sellhouse.IReceiveGather.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IReceiveGather getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IReceiveGather)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("15549BC3") ,com.kingdee.eas.fdc.sellhouse.IReceiveGather.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IReceiveGather getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IReceiveGather)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("15549BC3"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IReceiveGather getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IReceiveGather)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("15549BC3"));
    }
}