package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomPriceManageFactory
{
    private RoomPriceManageFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPriceManage getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPriceManage)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("33DCA7F8") ,com.kingdee.eas.fdc.sellhouse.IRoomPriceManage.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomPriceManage getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPriceManage)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("33DCA7F8") ,com.kingdee.eas.fdc.sellhouse.IRoomPriceManage.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPriceManage getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPriceManage)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("33DCA7F8"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPriceManage getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPriceManage)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("33DCA7F8"));
    }
}