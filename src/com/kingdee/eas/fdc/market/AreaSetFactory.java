package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AreaSetFactory
{
    private AreaSetFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IAreaSet getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IAreaSet)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("617A29A4") ,com.kingdee.eas.fdc.market.IAreaSet.class);
    }
    
    public static com.kingdee.eas.fdc.market.IAreaSet getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IAreaSet)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("617A29A4") ,com.kingdee.eas.fdc.market.IAreaSet.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IAreaSet getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IAreaSet)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("617A29A4"));
    }
    public static com.kingdee.eas.fdc.market.IAreaSet getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IAreaSet)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("617A29A4"));
    }
}