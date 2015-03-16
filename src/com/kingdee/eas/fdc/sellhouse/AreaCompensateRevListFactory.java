package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AreaCompensateRevListFactory
{
    private AreaCompensateRevListFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IAreaCompensateRevList getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAreaCompensateRevList)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("179EB1A2") ,com.kingdee.eas.fdc.sellhouse.IAreaCompensateRevList.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IAreaCompensateRevList getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAreaCompensateRevList)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("179EB1A2") ,com.kingdee.eas.fdc.sellhouse.IAreaCompensateRevList.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IAreaCompensateRevList getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAreaCompensateRevList)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("179EB1A2"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IAreaCompensateRevList getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAreaCompensateRevList)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("179EB1A2"));
    }
}