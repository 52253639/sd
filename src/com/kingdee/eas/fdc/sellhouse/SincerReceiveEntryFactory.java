package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SincerReceiveEntryFactory
{
    private SincerReceiveEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISincerReceiveEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISincerReceiveEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A0835602") ,com.kingdee.eas.fdc.sellhouse.ISincerReceiveEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISincerReceiveEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISincerReceiveEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A0835602") ,com.kingdee.eas.fdc.sellhouse.ISincerReceiveEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISincerReceiveEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISincerReceiveEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A0835602"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISincerReceiveEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISincerReceiveEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A0835602"));
    }
}