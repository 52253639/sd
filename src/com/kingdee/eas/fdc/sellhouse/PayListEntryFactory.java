package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PayListEntryFactory
{
    private PayListEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPayListEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPayListEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A46CBD07") ,com.kingdee.eas.fdc.sellhouse.IPayListEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPayListEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPayListEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A46CBD07") ,com.kingdee.eas.fdc.sellhouse.IPayListEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPayListEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPayListEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A46CBD07"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPayListEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPayListEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A46CBD07"));
    }
}