package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BasePriceRoomEntryFactory
{
    private BasePriceRoomEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IBasePriceRoomEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBasePriceRoomEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1B30505A") ,com.kingdee.eas.fdc.sellhouse.IBasePriceRoomEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IBasePriceRoomEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBasePriceRoomEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1B30505A") ,com.kingdee.eas.fdc.sellhouse.IBasePriceRoomEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IBasePriceRoomEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBasePriceRoomEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1B30505A"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IBasePriceRoomEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBasePriceRoomEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1B30505A"));
    }
}