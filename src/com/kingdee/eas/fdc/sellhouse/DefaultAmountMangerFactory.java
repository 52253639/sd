package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DefaultAmountMangerFactory
{
    private DefaultAmountMangerFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IDefaultAmountManger getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDefaultAmountManger)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("29845C58") ,com.kingdee.eas.fdc.sellhouse.IDefaultAmountManger.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IDefaultAmountManger getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDefaultAmountManger)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("29845C58") ,com.kingdee.eas.fdc.sellhouse.IDefaultAmountManger.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IDefaultAmountManger getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDefaultAmountManger)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("29845C58"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IDefaultAmountManger getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDefaultAmountManger)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("29845C58"));
    }
}