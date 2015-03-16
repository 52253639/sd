package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class UpdateDataFacadeFactory
{
    private UpdateDataFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IUpdateDataFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IUpdateDataFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7B3A6368") ,com.kingdee.eas.fdc.sellhouse.IUpdateDataFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IUpdateDataFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IUpdateDataFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7B3A6368") ,com.kingdee.eas.fdc.sellhouse.IUpdateDataFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IUpdateDataFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IUpdateDataFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7B3A6368"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IUpdateDataFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IUpdateDataFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7B3A6368"));
    }
}