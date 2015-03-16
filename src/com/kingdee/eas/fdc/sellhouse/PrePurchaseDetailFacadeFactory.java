package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PrePurchaseDetailFacadeFactory
{
    private PrePurchaseDetailFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPrePurchaseDetailFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPrePurchaseDetailFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("CA049DF4") ,com.kingdee.eas.fdc.sellhouse.IPrePurchaseDetailFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPrePurchaseDetailFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPrePurchaseDetailFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("CA049DF4") ,com.kingdee.eas.fdc.sellhouse.IPrePurchaseDetailFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPrePurchaseDetailFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPrePurchaseDetailFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("CA049DF4"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPrePurchaseDetailFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPrePurchaseDetailFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("CA049DF4"));
    }
}