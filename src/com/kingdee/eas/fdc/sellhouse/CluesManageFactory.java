package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CluesManageFactory
{
    private CluesManageFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ICluesManage getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICluesManage)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EA85C324") ,com.kingdee.eas.fdc.sellhouse.ICluesManage.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ICluesManage getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICluesManage)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EA85C324") ,com.kingdee.eas.fdc.sellhouse.ICluesManage.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ICluesManage getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICluesManage)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EA85C324"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ICluesManage getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICluesManage)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EA85C324"));
    }
}