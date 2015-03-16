package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DayInitDataBldFactory
{
    private DayInitDataBldFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IDayInitDataBld getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDayInitDataBld)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("96C3347F") ,com.kingdee.eas.fdc.sellhouse.IDayInitDataBld.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IDayInitDataBld getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDayInitDataBld)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("96C3347F") ,com.kingdee.eas.fdc.sellhouse.IDayInitDataBld.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IDayInitDataBld getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDayInitDataBld)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("96C3347F"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IDayInitDataBld getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDayInitDataBld)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("96C3347F"));
    }
}