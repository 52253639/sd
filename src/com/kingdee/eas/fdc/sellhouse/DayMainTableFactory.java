package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DayMainTableFactory
{
    private DayMainTableFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IDayMainTable getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDayMainTable)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("82E5D3B4") ,com.kingdee.eas.fdc.sellhouse.IDayMainTable.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IDayMainTable getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDayMainTable)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("82E5D3B4") ,com.kingdee.eas.fdc.sellhouse.IDayMainTable.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IDayMainTable getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDayMainTable)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("82E5D3B4"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IDayMainTable getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDayMainTable)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("82E5D3B4"));
    }
}