package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BalanceAdjustFacadeFactory
{
    private BalanceAdjustFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IBalanceAdjustFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBalanceAdjustFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FA5F434A") ,com.kingdee.eas.fdc.sellhouse.IBalanceAdjustFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IBalanceAdjustFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBalanceAdjustFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FA5F434A") ,com.kingdee.eas.fdc.sellhouse.IBalanceAdjustFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IBalanceAdjustFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBalanceAdjustFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FA5F434A"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IBalanceAdjustFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBalanceAdjustFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FA5F434A"));
    }
}