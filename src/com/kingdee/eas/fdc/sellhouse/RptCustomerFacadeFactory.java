package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RptCustomerFacadeFactory
{
    private RptCustomerFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRptCustomerFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRptCustomerFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3BF6E833") ,com.kingdee.eas.fdc.sellhouse.IRptCustomerFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRptCustomerFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRptCustomerFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3BF6E833") ,com.kingdee.eas.fdc.sellhouse.IRptCustomerFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRptCustomerFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRptCustomerFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3BF6E833"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRptCustomerFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRptCustomerFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3BF6E833"));
    }
}