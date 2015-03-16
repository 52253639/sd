package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BoutiqueRoomTypeTreeFactory
{
    private BoutiqueRoomTypeTreeFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IBoutiqueRoomTypeTree getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IBoutiqueRoomTypeTree)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D379CCC8") ,com.kingdee.eas.fdc.market.IBoutiqueRoomTypeTree.class);
    }
    
    public static com.kingdee.eas.fdc.market.IBoutiqueRoomTypeTree getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IBoutiqueRoomTypeTree)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D379CCC8") ,com.kingdee.eas.fdc.market.IBoutiqueRoomTypeTree.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IBoutiqueRoomTypeTree getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IBoutiqueRoomTypeTree)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D379CCC8"));
    }
    public static com.kingdee.eas.fdc.market.IBoutiqueRoomTypeTree getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IBoutiqueRoomTypeTree)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D379CCC8"));
    }
}