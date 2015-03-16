package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BirdEyeFactory
{
    private BirdEyeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IBirdEye getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBirdEye)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4789C37D") ,com.kingdee.eas.fdc.sellhouse.IBirdEye.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IBirdEye getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBirdEye)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4789C37D") ,com.kingdee.eas.fdc.sellhouse.IBirdEye.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IBirdEye getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBirdEye)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4789C37D"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IBirdEye getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBirdEye)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4789C37D"));
    }
}