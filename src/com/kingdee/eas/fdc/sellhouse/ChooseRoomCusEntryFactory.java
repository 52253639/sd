package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChooseRoomCusEntryFactory
{
    private ChooseRoomCusEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IChooseRoomCusEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChooseRoomCusEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BA24967E") ,com.kingdee.eas.fdc.sellhouse.IChooseRoomCusEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IChooseRoomCusEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChooseRoomCusEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BA24967E") ,com.kingdee.eas.fdc.sellhouse.IChooseRoomCusEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IChooseRoomCusEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChooseRoomCusEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BA24967E"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IChooseRoomCusEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChooseRoomCusEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BA24967E"));
    }
}