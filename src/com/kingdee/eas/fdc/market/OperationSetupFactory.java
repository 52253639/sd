package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OperationSetupFactory
{
    private OperationSetupFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IOperationSetup getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IOperationSetup)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("23A52167") ,com.kingdee.eas.fdc.market.IOperationSetup.class);
    }
    
    public static com.kingdee.eas.fdc.market.IOperationSetup getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IOperationSetup)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("23A52167") ,com.kingdee.eas.fdc.market.IOperationSetup.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IOperationSetup getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IOperationSetup)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("23A52167"));
    }
    public static com.kingdee.eas.fdc.market.IOperationSetup getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IOperationSetup)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("23A52167"));
    }
}