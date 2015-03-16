package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CommerceLevelFactory
{
    private CommerceLevelFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ICommerceLevel getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICommerceLevel)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B372266E") ,com.kingdee.eas.fdc.sellhouse.ICommerceLevel.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ICommerceLevel getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICommerceLevel)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B372266E") ,com.kingdee.eas.fdc.sellhouse.ICommerceLevel.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ICommerceLevel getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICommerceLevel)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B372266E"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ICommerceLevel getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICommerceLevel)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B372266E"));
    }
}