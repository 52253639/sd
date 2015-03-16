package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProductDetialFactory
{
    private ProductDetialFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IProductDetial getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IProductDetial)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F4D91435") ,com.kingdee.eas.fdc.sellhouse.IProductDetial.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IProductDetial getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IProductDetial)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F4D91435") ,com.kingdee.eas.fdc.sellhouse.IProductDetial.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IProductDetial getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IProductDetial)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F4D91435"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IProductDetial getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IProductDetial)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F4D91435"));
    }
}