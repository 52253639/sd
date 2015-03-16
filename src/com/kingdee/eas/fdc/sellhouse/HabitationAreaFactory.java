package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class HabitationAreaFactory
{
    private HabitationAreaFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IHabitationArea getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IHabitationArea)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DE3809C9") ,com.kingdee.eas.fdc.sellhouse.IHabitationArea.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IHabitationArea getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IHabitationArea)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DE3809C9") ,com.kingdee.eas.fdc.sellhouse.IHabitationArea.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IHabitationArea getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IHabitationArea)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DE3809C9"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IHabitationArea getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IHabitationArea)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DE3809C9"));
    }
}