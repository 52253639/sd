package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SHEProjectSetFactory
{
    private SHEProjectSetFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEProjectSet getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEProjectSet)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("42F0C45E") ,com.kingdee.eas.fdc.sellhouse.ISHEProjectSet.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISHEProjectSet getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEProjectSet)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("42F0C45E") ,com.kingdee.eas.fdc.sellhouse.ISHEProjectSet.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEProjectSet getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEProjectSet)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("42F0C45E"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEProjectSet getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEProjectSet)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("42F0C45E"));
    }
}