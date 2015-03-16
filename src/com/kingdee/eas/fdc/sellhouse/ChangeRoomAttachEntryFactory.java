package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChangeRoomAttachEntryFactory
{
    private ChangeRoomAttachEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangeRoomAttachEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeRoomAttachEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("51E1BB87") ,com.kingdee.eas.fdc.sellhouse.IChangeRoomAttachEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IChangeRoomAttachEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeRoomAttachEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("51E1BB87") ,com.kingdee.eas.fdc.sellhouse.IChangeRoomAttachEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangeRoomAttachEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeRoomAttachEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("51E1BB87"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangeRoomAttachEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeRoomAttachEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("51E1BB87"));
    }
}