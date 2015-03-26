package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class LinkReportFacadeFactory
{
    private LinkReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ILinkReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ILinkReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E7E957B1") ,com.kingdee.eas.fdc.tenancy.ILinkReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ILinkReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ILinkReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E7E957B1") ,com.kingdee.eas.fdc.tenancy.ILinkReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ILinkReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ILinkReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E7E957B1"));
    }
    public static com.kingdee.eas.fdc.tenancy.ILinkReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ILinkReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E7E957B1"));
    }
}