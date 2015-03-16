package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BatchManageFactory
{
    private BatchManageFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IBatchManage getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBatchManage)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2C8DDF84") ,com.kingdee.eas.fdc.sellhouse.IBatchManage.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IBatchManage getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBatchManage)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2C8DDF84") ,com.kingdee.eas.fdc.sellhouse.IBatchManage.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IBatchManage getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBatchManage)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2C8DDF84"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IBatchManage getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBatchManage)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2C8DDF84"));
    }
}