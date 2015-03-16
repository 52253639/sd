package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RevUpFacadeFactory
{
    private RevUpFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRevUpFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRevUpFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6172271D") ,com.kingdee.eas.fdc.sellhouse.IRevUpFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRevUpFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRevUpFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6172271D") ,com.kingdee.eas.fdc.sellhouse.IRevUpFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRevUpFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRevUpFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6172271D"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRevUpFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRevUpFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6172271D"));
    }
}