package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DayInitDataPtyFactory
{
    private DayInitDataPtyFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IDayInitDataPty getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDayInitDataPty)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("96C36A1A") ,com.kingdee.eas.fdc.sellhouse.IDayInitDataPty.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IDayInitDataPty getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDayInitDataPty)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("96C36A1A") ,com.kingdee.eas.fdc.sellhouse.IDayInitDataPty.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IDayInitDataPty getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDayInitDataPty)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("96C36A1A"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IDayInitDataPty getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDayInitDataPty)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("96C36A1A"));
    }
}