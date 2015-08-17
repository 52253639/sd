package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RevDetailInvReportFacadeFactory
{
    private RevDetailInvReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IRevDetailInvReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRevDetailInvReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D2248AB4") ,com.kingdee.eas.fdc.tenancy.IRevDetailInvReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IRevDetailInvReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRevDetailInvReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D2248AB4") ,com.kingdee.eas.fdc.tenancy.IRevDetailInvReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IRevDetailInvReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRevDetailInvReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D2248AB4"));
    }
    public static com.kingdee.eas.fdc.tenancy.IRevDetailInvReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRevDetailInvReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D2248AB4"));
    }
}