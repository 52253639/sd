package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class HabitationRecordFactory
{
    private HabitationRecordFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IHabitationRecord getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IHabitationRecord)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4CA7468D") ,com.kingdee.eas.fdc.sellhouse.IHabitationRecord.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IHabitationRecord getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IHabitationRecord)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4CA7468D") ,com.kingdee.eas.fdc.sellhouse.IHabitationRecord.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IHabitationRecord getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IHabitationRecord)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4CA7468D"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IHabitationRecord getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IHabitationRecord)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4CA7468D"));
    }
}