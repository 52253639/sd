package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OverdueDescribeFactory
{
    private OverdueDescribeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IOverdueDescribe getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IOverdueDescribe)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("030F4B50") ,com.kingdee.eas.fdc.sellhouse.IOverdueDescribe.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IOverdueDescribe getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IOverdueDescribe)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("030F4B50") ,com.kingdee.eas.fdc.sellhouse.IOverdueDescribe.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IOverdueDescribe getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IOverdueDescribe)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("030F4B50"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IOverdueDescribe getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IOverdueDescribe)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("030F4B50"));
    }
}