package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DynamicValueReportFacadeFactory
{
    private DynamicValueReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IDynamicValueReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IDynamicValueReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("16644751") ,com.kingdee.eas.fdc.market.IDynamicValueReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.market.IDynamicValueReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IDynamicValueReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("16644751") ,com.kingdee.eas.fdc.market.IDynamicValueReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IDynamicValueReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IDynamicValueReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("16644751"));
    }
    public static com.kingdee.eas.fdc.market.IDynamicValueReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IDynamicValueReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("16644751"));
    }
}