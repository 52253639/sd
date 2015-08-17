package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TenancyAllReportFacadeFactory
{
    private TenancyAllReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyAllReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyAllReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BFCCE8F8") ,com.kingdee.eas.fdc.tenancy.ITenancyAllReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ITenancyAllReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyAllReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BFCCE8F8") ,com.kingdee.eas.fdc.tenancy.ITenancyAllReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyAllReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyAllReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BFCCE8F8"));
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyAllReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyAllReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BFCCE8F8"));
    }
}