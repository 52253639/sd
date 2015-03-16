package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SignManageFactory
{
    private SignManageFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISignManage getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISignManage)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D840369D") ,com.kingdee.eas.fdc.sellhouse.ISignManage.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISignManage getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISignManage)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D840369D") ,com.kingdee.eas.fdc.sellhouse.ISignManage.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISignManage getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISignManage)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D840369D"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISignManage getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISignManage)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D840369D"));
    }
}