package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PrePurchaseRoomAttachmentEntryFactory
{
    private PrePurchaseRoomAttachmentEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPrePurchaseRoomAttachmentEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPrePurchaseRoomAttachmentEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FAA358EB") ,com.kingdee.eas.fdc.sellhouse.IPrePurchaseRoomAttachmentEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPrePurchaseRoomAttachmentEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPrePurchaseRoomAttachmentEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FAA358EB") ,com.kingdee.eas.fdc.sellhouse.IPrePurchaseRoomAttachmentEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPrePurchaseRoomAttachmentEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPrePurchaseRoomAttachmentEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FAA358EB"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPrePurchaseRoomAttachmentEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPrePurchaseRoomAttachmentEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FAA358EB"));
    }
}