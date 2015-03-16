package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DynamicTotalValueFacadeFactory
{
    private DynamicTotalValueFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IDynamicTotalValueFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IDynamicTotalValueFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8F1A54F5") ,com.kingdee.eas.fdc.market.IDynamicTotalValueFacade.class);
    }
    
    public static com.kingdee.eas.fdc.market.IDynamicTotalValueFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IDynamicTotalValueFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8F1A54F5") ,com.kingdee.eas.fdc.market.IDynamicTotalValueFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IDynamicTotalValueFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IDynamicTotalValueFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8F1A54F5"));
    }
    public static com.kingdee.eas.fdc.market.IDynamicTotalValueFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IDynamicTotalValueFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8F1A54F5"));
    }
}