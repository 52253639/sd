package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SignAgioEntryFactory
{
    private SignAgioEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISignAgioEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISignAgioEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E230ABCE") ,com.kingdee.eas.fdc.sellhouse.ISignAgioEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISignAgioEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISignAgioEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E230ABCE") ,com.kingdee.eas.fdc.sellhouse.ISignAgioEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISignAgioEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISignAgioEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E230ABCE"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISignAgioEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISignAgioEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E230ABCE"));
    }
}