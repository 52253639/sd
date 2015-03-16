package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PurchaseChangeRoomAttachmentOldEntryFactory
{
    private PurchaseChangeRoomAttachmentOldEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangeRoomAttachmentOldEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangeRoomAttachmentOldEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B2629FD5") ,com.kingdee.eas.fdc.sellhouse.IPurchaseChangeRoomAttachmentOldEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangeRoomAttachmentOldEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangeRoomAttachmentOldEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B2629FD5") ,com.kingdee.eas.fdc.sellhouse.IPurchaseChangeRoomAttachmentOldEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangeRoomAttachmentOldEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangeRoomAttachmentOldEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B2629FD5"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangeRoomAttachmentOldEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangeRoomAttachmentOldEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B2629FD5"));
    }
}