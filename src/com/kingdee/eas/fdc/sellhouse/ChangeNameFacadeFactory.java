package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChangeNameFacadeFactory
{
    private ChangeNameFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangeNameFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeNameFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6A7527B0") ,com.kingdee.eas.fdc.sellhouse.IChangeNameFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IChangeNameFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeNameFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6A7527B0") ,com.kingdee.eas.fdc.sellhouse.IChangeNameFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangeNameFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeNameFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6A7527B0"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangeNameFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeNameFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6A7527B0"));
    }
}