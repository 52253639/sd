package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PurchaseFacadeFactory
{
    private PurchaseFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9796E896") ,com.kingdee.eas.fdc.sellhouse.IPurchaseFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9796E896") ,com.kingdee.eas.fdc.sellhouse.IPurchaseFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9796E896"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9796E896"));
    }
}