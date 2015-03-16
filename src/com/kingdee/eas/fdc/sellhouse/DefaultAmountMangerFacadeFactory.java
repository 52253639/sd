package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DefaultAmountMangerFacadeFactory
{
    private DefaultAmountMangerFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IDefaultAmountMangerFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDefaultAmountMangerFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5FEC5712") ,com.kingdee.eas.fdc.sellhouse.IDefaultAmountMangerFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IDefaultAmountMangerFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDefaultAmountMangerFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5FEC5712") ,com.kingdee.eas.fdc.sellhouse.IDefaultAmountMangerFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IDefaultAmountMangerFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDefaultAmountMangerFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5FEC5712"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IDefaultAmountMangerFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDefaultAmountMangerFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5FEC5712"));
    }
}