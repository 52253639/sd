package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PurRoomAttachmentEntryFactory
{
    private PurRoomAttachmentEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurRoomAttachmentEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurRoomAttachmentEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("12F21B02") ,com.kingdee.eas.fdc.sellhouse.IPurRoomAttachmentEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPurRoomAttachmentEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurRoomAttachmentEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("12F21B02") ,com.kingdee.eas.fdc.sellhouse.IPurRoomAttachmentEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurRoomAttachmentEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurRoomAttachmentEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("12F21B02"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurRoomAttachmentEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurRoomAttachmentEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("12F21B02"));
    }
}