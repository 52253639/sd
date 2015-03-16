package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CommerceChanceFactory
{
    private CommerceChanceFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ICommerceChance getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICommerceChance)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("AB97E58A") ,com.kingdee.eas.fdc.sellhouse.ICommerceChance.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ICommerceChance getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICommerceChance)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("AB97E58A") ,com.kingdee.eas.fdc.sellhouse.ICommerceChance.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ICommerceChance getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICommerceChance)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("AB97E58A"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ICommerceChance getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICommerceChance)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("AB97E58A"));
    }
}