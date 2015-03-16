package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProductTypeSellStateFacadeFactory
{
    private ProductTypeSellStateFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IProductTypeSellStateFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IProductTypeSellStateFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("51E8C8EB") ,com.kingdee.eas.fdc.sellhouse.IProductTypeSellStateFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IProductTypeSellStateFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IProductTypeSellStateFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("51E8C8EB") ,com.kingdee.eas.fdc.sellhouse.IProductTypeSellStateFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IProductTypeSellStateFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IProductTypeSellStateFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("51E8C8EB"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IProductTypeSellStateFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IProductTypeSellStateFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("51E8C8EB"));
    }
}