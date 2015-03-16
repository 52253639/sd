package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PurchaseChangeRoomAttachmentEntryFactory
{
    private PurchaseChangeRoomAttachmentEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangeRoomAttachmentEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangeRoomAttachmentEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("810BA768") ,com.kingdee.eas.fdc.sellhouse.IPurchaseChangeRoomAttachmentEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangeRoomAttachmentEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangeRoomAttachmentEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("810BA768") ,com.kingdee.eas.fdc.sellhouse.IPurchaseChangeRoomAttachmentEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangeRoomAttachmentEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangeRoomAttachmentEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("810BA768"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangeRoomAttachmentEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangeRoomAttachmentEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("810BA768"));
    }
}