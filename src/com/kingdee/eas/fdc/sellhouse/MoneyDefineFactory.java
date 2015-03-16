package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MoneyDefineFactory
{
    private MoneyDefineFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IMoneyDefine getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMoneyDefine)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B8B0A8E0") ,com.kingdee.eas.fdc.sellhouse.IMoneyDefine.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IMoneyDefine getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMoneyDefine)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B8B0A8E0") ,com.kingdee.eas.fdc.sellhouse.IMoneyDefine.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IMoneyDefine getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMoneyDefine)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B8B0A8E0"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IMoneyDefine getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMoneyDefine)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B8B0A8E0"));
    }
}