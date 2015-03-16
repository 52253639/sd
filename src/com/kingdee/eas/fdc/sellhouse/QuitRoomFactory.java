package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class QuitRoomFactory
{
    private QuitRoomFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IQuitRoom getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IQuitRoom)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("771FC2CB") ,com.kingdee.eas.fdc.sellhouse.IQuitRoom.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IQuitRoom getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IQuitRoom)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("771FC2CB") ,com.kingdee.eas.fdc.sellhouse.IQuitRoom.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IQuitRoom getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IQuitRoom)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("771FC2CB"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IQuitRoom getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IQuitRoom)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("771FC2CB"));
    }
}