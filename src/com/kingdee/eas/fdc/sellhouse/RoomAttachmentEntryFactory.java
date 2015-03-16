package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomAttachmentEntryFactory
{
    private RoomAttachmentEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomAttachmentEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomAttachmentEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("05680093") ,com.kingdee.eas.fdc.sellhouse.IRoomAttachmentEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomAttachmentEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomAttachmentEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("05680093") ,com.kingdee.eas.fdc.sellhouse.IRoomAttachmentEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomAttachmentEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomAttachmentEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("05680093"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomAttachmentEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomAttachmentEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("05680093"));
    }
}