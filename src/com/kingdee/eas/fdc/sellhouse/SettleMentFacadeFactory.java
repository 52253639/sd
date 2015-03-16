package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SettleMentFacadeFactory
{
    private SettleMentFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISettleMentFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISettleMentFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D528101E") ,com.kingdee.eas.fdc.sellhouse.ISettleMentFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISettleMentFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISettleMentFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D528101E") ,com.kingdee.eas.fdc.sellhouse.ISettleMentFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISettleMentFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISettleMentFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D528101E"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISettleMentFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISettleMentFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D528101E"));
    }
}