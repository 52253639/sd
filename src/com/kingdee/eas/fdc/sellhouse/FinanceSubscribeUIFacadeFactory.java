package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FinanceSubscribeUIFacadeFactory
{
    private FinanceSubscribeUIFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IFinanceSubscribeUIFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFinanceSubscribeUIFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BCEE00B9") ,com.kingdee.eas.fdc.sellhouse.IFinanceSubscribeUIFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IFinanceSubscribeUIFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFinanceSubscribeUIFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BCEE00B9") ,com.kingdee.eas.fdc.sellhouse.IFinanceSubscribeUIFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IFinanceSubscribeUIFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFinanceSubscribeUIFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BCEE00B9"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IFinanceSubscribeUIFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFinanceSubscribeUIFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BCEE00B9"));
    }
}