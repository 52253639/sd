package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AfterServiceFactory
{
    private AfterServiceFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IAfterService getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAfterService)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C1C33F94") ,com.kingdee.eas.fdc.sellhouse.IAfterService.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IAfterService getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAfterService)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C1C33F94") ,com.kingdee.eas.fdc.sellhouse.IAfterService.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IAfterService getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAfterService)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C1C33F94"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IAfterService getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAfterService)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C1C33F94"));
    }
}