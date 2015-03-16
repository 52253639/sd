package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SHEFunctionSetEntryFactory
{
    private SHEFunctionSetEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEFunctionSetEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEFunctionSetEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0871233D") ,com.kingdee.eas.fdc.sellhouse.ISHEFunctionSetEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISHEFunctionSetEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEFunctionSetEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0871233D") ,com.kingdee.eas.fdc.sellhouse.ISHEFunctionSetEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEFunctionSetEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEFunctionSetEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0871233D"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEFunctionSetEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEFunctionSetEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0871233D"));
    }
}