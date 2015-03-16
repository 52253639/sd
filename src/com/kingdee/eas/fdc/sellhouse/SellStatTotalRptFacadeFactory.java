package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SellStatTotalRptFacadeFactory
{
    private SellStatTotalRptFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISellStatTotalRptFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISellStatTotalRptFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D2D7824D") ,com.kingdee.eas.fdc.sellhouse.ISellStatTotalRptFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISellStatTotalRptFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISellStatTotalRptFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D2D7824D") ,com.kingdee.eas.fdc.sellhouse.ISellStatTotalRptFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISellStatTotalRptFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISellStatTotalRptFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D2D7824D"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISellStatTotalRptFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISellStatTotalRptFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D2D7824D"));
    }
}