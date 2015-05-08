package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TraEntryFactory
{
    private TraEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.ITraEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ITraEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BBC4FBCA") ,com.kingdee.eas.fdc.contract.ITraEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.ITraEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ITraEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BBC4FBCA") ,com.kingdee.eas.fdc.contract.ITraEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.ITraEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ITraEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BBC4FBCA"));
    }
    public static com.kingdee.eas.fdc.contract.ITraEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ITraEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BBC4FBCA"));
    }
}