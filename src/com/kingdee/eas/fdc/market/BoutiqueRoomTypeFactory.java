package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BoutiqueRoomTypeFactory
{
    private BoutiqueRoomTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IBoutiqueRoomType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IBoutiqueRoomType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A051FA0A") ,com.kingdee.eas.fdc.market.IBoutiqueRoomType.class);
    }
    
    public static com.kingdee.eas.fdc.market.IBoutiqueRoomType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IBoutiqueRoomType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A051FA0A") ,com.kingdee.eas.fdc.market.IBoutiqueRoomType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IBoutiqueRoomType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IBoutiqueRoomType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A051FA0A"));
    }
    public static com.kingdee.eas.fdc.market.IBoutiqueRoomType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IBoutiqueRoomType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A051FA0A"));
    }
}