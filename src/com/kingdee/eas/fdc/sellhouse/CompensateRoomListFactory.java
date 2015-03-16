package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CompensateRoomListFactory
{
    private CompensateRoomListFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ICompensateRoomList getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICompensateRoomList)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5EC8BC8B") ,com.kingdee.eas.fdc.sellhouse.ICompensateRoomList.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ICompensateRoomList getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICompensateRoomList)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5EC8BC8B") ,com.kingdee.eas.fdc.sellhouse.ICompensateRoomList.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ICompensateRoomList getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICompensateRoomList)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5EC8BC8B"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ICompensateRoomList getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICompensateRoomList)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5EC8BC8B"));
    }
}