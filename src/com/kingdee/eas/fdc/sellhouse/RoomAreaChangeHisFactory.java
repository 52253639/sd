package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomAreaChangeHisFactory
{
    private RoomAreaChangeHisFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomAreaChangeHis getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomAreaChangeHis)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BD7BBD1F") ,com.kingdee.eas.fdc.sellhouse.IRoomAreaChangeHis.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomAreaChangeHis getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomAreaChangeHis)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BD7BBD1F") ,com.kingdee.eas.fdc.sellhouse.IRoomAreaChangeHis.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomAreaChangeHis getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomAreaChangeHis)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BD7BBD1F"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomAreaChangeHis getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomAreaChangeHis)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BD7BBD1F"));
    }
}