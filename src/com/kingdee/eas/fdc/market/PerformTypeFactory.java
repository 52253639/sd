package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PerformTypeFactory
{
    private PerformTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IPerformType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IPerformType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1EF2CFCA") ,com.kingdee.eas.fdc.market.IPerformType.class);
    }
    
    public static com.kingdee.eas.fdc.market.IPerformType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IPerformType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1EF2CFCA") ,com.kingdee.eas.fdc.market.IPerformType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IPerformType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IPerformType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1EF2CFCA"));
    }
    public static com.kingdee.eas.fdc.market.IPerformType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IPerformType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1EF2CFCA"));
    }
}