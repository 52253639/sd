package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PublicCustomerFacadeFactory
{
    private PublicCustomerFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPublicCustomerFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPublicCustomerFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C6D23C3C") ,com.kingdee.eas.fdc.sellhouse.IPublicCustomerFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPublicCustomerFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPublicCustomerFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C6D23C3C") ,com.kingdee.eas.fdc.sellhouse.IPublicCustomerFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPublicCustomerFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPublicCustomerFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C6D23C3C"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPublicCustomerFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPublicCustomerFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C6D23C3C"));
    }
}