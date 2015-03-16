package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DeveloperManageFactory
{
    private DeveloperManageFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IDeveloperManage getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IDeveloperManage)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9B500FDE") ,com.kingdee.eas.fdc.market.IDeveloperManage.class);
    }
    
    public static com.kingdee.eas.fdc.market.IDeveloperManage getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IDeveloperManage)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9B500FDE") ,com.kingdee.eas.fdc.market.IDeveloperManage.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IDeveloperManage getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IDeveloperManage)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9B500FDE"));
    }
    public static com.kingdee.eas.fdc.market.IDeveloperManage getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IDeveloperManage)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9B500FDE"));
    }
}