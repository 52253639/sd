package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PurchaseRoomAttachmentEntryFactory
{
    private PurchaseRoomAttachmentEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseRoomAttachmentEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseRoomAttachmentEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6668A438") ,com.kingdee.eas.fdc.sellhouse.IPurchaseRoomAttachmentEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseRoomAttachmentEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseRoomAttachmentEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6668A438") ,com.kingdee.eas.fdc.sellhouse.IPurchaseRoomAttachmentEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseRoomAttachmentEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseRoomAttachmentEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6668A438"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseRoomAttachmentEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseRoomAttachmentEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6668A438"));
    }
}