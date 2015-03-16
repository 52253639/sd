package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PushManageHisEntryFactory
{
    private PushManageHisEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPushManageHisEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPushManageHisEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9995485A") ,com.kingdee.eas.fdc.sellhouse.IPushManageHisEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPushManageHisEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPushManageHisEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9995485A") ,com.kingdee.eas.fdc.sellhouse.IPushManageHisEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPushManageHisEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPushManageHisEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9995485A"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPushManageHisEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPushManageHisEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9995485A"));
    }
}