package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ActivityValueReportFacadeFactory
{
    private ActivityValueReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IActivityValueReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IActivityValueReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("AFA182FF") ,com.kingdee.eas.fdc.market.IActivityValueReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.market.IActivityValueReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IActivityValueReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("AFA182FF") ,com.kingdee.eas.fdc.market.IActivityValueReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IActivityValueReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IActivityValueReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("AFA182FF"));
    }
    public static com.kingdee.eas.fdc.market.IActivityValueReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IActivityValueReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("AFA182FF"));
    }
}