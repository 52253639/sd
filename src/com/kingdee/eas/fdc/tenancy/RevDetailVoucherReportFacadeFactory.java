package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RevDetailVoucherReportFacadeFactory
{
    private RevDetailVoucherReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IRevDetailVoucherReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRevDetailVoucherReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("17C6AB71") ,com.kingdee.eas.fdc.tenancy.IRevDetailVoucherReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IRevDetailVoucherReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRevDetailVoucherReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("17C6AB71") ,com.kingdee.eas.fdc.tenancy.IRevDetailVoucherReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IRevDetailVoucherReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRevDetailVoucherReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("17C6AB71"));
    }
    public static com.kingdee.eas.fdc.tenancy.IRevDetailVoucherReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRevDetailVoucherReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("17C6AB71"));
    }
}