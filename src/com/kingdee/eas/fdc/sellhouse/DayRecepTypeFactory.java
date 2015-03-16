package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DayRecepTypeFactory
{
    private DayRecepTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IDayRecepType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDayRecepType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BC933D14") ,com.kingdee.eas.fdc.sellhouse.IDayRecepType.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IDayRecepType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDayRecepType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BC933D14") ,com.kingdee.eas.fdc.sellhouse.IDayRecepType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IDayRecepType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDayRecepType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BC933D14"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IDayRecepType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDayRecepType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BC933D14"));
    }
}