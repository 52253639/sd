package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SHEFunctionSetFactory
{
    private SHEFunctionSetFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEFunctionSet getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEFunctionSet)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DF288E35") ,com.kingdee.eas.fdc.sellhouse.ISHEFunctionSet.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISHEFunctionSet getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEFunctionSet)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DF288E35") ,com.kingdee.eas.fdc.sellhouse.ISHEFunctionSet.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEFunctionSet getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEFunctionSet)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DF288E35"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEFunctionSet getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEFunctionSet)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DF288E35"));
    }
}