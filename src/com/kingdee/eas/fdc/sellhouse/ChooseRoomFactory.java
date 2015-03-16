package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChooseRoomFactory
{
    private ChooseRoomFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IChooseRoom getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChooseRoom)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B45858AD") ,com.kingdee.eas.fdc.sellhouse.IChooseRoom.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IChooseRoom getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChooseRoom)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B45858AD") ,com.kingdee.eas.fdc.sellhouse.IChooseRoom.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IChooseRoom getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChooseRoom)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B45858AD"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IChooseRoom getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChooseRoom)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B45858AD"));
    }
}