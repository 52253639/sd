package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DayAccumulateBldFactory
{
    private DayAccumulateBldFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IDayAccumulateBld getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDayAccumulateBld)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D9136737") ,com.kingdee.eas.fdc.sellhouse.IDayAccumulateBld.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IDayAccumulateBld getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDayAccumulateBld)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D9136737") ,com.kingdee.eas.fdc.sellhouse.IDayAccumulateBld.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IDayAccumulateBld getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDayAccumulateBld)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D9136737"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IDayAccumulateBld getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDayAccumulateBld)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D9136737"));
    }
}