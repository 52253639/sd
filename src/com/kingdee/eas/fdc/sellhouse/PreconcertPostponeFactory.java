package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PreconcertPostponeFactory
{
    private PreconcertPostponeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPreconcertPostpone getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPreconcertPostpone)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("59919274") ,com.kingdee.eas.fdc.sellhouse.IPreconcertPostpone.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPreconcertPostpone getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPreconcertPostpone)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("59919274") ,com.kingdee.eas.fdc.sellhouse.IPreconcertPostpone.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPreconcertPostpone getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPreconcertPostpone)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("59919274"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPreconcertPostpone getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPreconcertPostpone)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("59919274"));
    }
}