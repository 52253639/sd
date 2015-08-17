package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RevDetailFinReportFacadeFactory
{
    private RevDetailFinReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IRevDetailFinReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRevDetailFinReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E72527CE") ,com.kingdee.eas.fdc.tenancy.IRevDetailFinReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IRevDetailFinReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRevDetailFinReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E72527CE") ,com.kingdee.eas.fdc.tenancy.IRevDetailFinReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IRevDetailFinReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRevDetailFinReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E72527CE"));
    }
    public static com.kingdee.eas.fdc.tenancy.IRevDetailFinReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRevDetailFinReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E72527CE"));
    }
}