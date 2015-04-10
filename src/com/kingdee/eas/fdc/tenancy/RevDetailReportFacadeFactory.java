package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RevDetailReportFacadeFactory
{
    private RevDetailReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IRevDetailReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRevDetailReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C62189D9") ,com.kingdee.eas.fdc.tenancy.IRevDetailReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IRevDetailReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRevDetailReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C62189D9") ,com.kingdee.eas.fdc.tenancy.IRevDetailReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IRevDetailReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRevDetailReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C62189D9"));
    }
    public static com.kingdee.eas.fdc.tenancy.IRevDetailReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRevDetailReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C62189D9"));
    }
}