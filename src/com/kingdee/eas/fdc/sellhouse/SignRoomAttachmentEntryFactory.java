package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SignRoomAttachmentEntryFactory
{
    private SignRoomAttachmentEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISignRoomAttachmentEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISignRoomAttachmentEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DC57247C") ,com.kingdee.eas.fdc.sellhouse.ISignRoomAttachmentEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISignRoomAttachmentEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISignRoomAttachmentEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DC57247C") ,com.kingdee.eas.fdc.sellhouse.ISignRoomAttachmentEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISignRoomAttachmentEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISignRoomAttachmentEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DC57247C"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISignRoomAttachmentEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISignRoomAttachmentEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DC57247C"));
    }
}