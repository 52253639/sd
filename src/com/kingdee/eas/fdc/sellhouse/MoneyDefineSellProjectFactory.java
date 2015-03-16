package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MoneyDefineSellProjectFactory
{
    private MoneyDefineSellProjectFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IMoneyDefineSellProject getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMoneyDefineSellProject)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A10D4667") ,com.kingdee.eas.fdc.sellhouse.IMoneyDefineSellProject.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IMoneyDefineSellProject getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMoneyDefineSellProject)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A10D4667") ,com.kingdee.eas.fdc.sellhouse.IMoneyDefineSellProject.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IMoneyDefineSellProject getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMoneyDefineSellProject)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A10D4667"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IMoneyDefineSellProject getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMoneyDefineSellProject)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A10D4667"));
    }
}