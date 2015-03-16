package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class IntentionRoomsEntryFactory
{
    private IntentionRoomsEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IIntentionRoomsEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IIntentionRoomsEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("669F46AB") ,com.kingdee.eas.fdc.sellhouse.IIntentionRoomsEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IIntentionRoomsEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IIntentionRoomsEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("669F46AB") ,com.kingdee.eas.fdc.sellhouse.IIntentionRoomsEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IIntentionRoomsEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IIntentionRoomsEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("669F46AB"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IIntentionRoomsEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IIntentionRoomsEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("669F46AB"));
    }
}