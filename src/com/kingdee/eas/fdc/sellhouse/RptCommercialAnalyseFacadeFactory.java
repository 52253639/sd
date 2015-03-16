package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RptCommercialAnalyseFacadeFactory
{
    private RptCommercialAnalyseFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRptCommercialAnalyseFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRptCommercialAnalyseFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7773B808") ,com.kingdee.eas.fdc.sellhouse.IRptCommercialAnalyseFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRptCommercialAnalyseFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRptCommercialAnalyseFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7773B808") ,com.kingdee.eas.fdc.sellhouse.IRptCommercialAnalyseFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRptCommercialAnalyseFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRptCommercialAnalyseFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7773B808"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRptCommercialAnalyseFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRptCommercialAnalyseFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7773B808"));
    }
}