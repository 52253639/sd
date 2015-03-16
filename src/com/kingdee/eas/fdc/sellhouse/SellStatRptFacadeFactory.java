package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SellStatRptFacadeFactory
{
    private SellStatRptFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISellStatRptFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISellStatRptFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("89089E8F") ,com.kingdee.eas.fdc.sellhouse.ISellStatRptFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISellStatRptFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISellStatRptFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("89089E8F") ,com.kingdee.eas.fdc.sellhouse.ISellStatRptFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISellStatRptFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISellStatRptFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("89089E8F"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISellStatRptFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISellStatRptFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("89089E8F"));
    }
}