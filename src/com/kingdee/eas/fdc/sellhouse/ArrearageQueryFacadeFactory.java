package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ArrearageQueryFacadeFactory
{
    private ArrearageQueryFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IArrearageQueryFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IArrearageQueryFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EDA9A8E9") ,com.kingdee.eas.fdc.sellhouse.IArrearageQueryFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IArrearageQueryFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IArrearageQueryFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EDA9A8E9") ,com.kingdee.eas.fdc.sellhouse.IArrearageQueryFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IArrearageQueryFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IArrearageQueryFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EDA9A8E9"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IArrearageQueryFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IArrearageQueryFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EDA9A8E9"));
    }
}