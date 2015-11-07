package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OtherBillReportFacadeFactory
{
    private OtherBillReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IOtherBillReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IOtherBillReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C8E6A5BC") ,com.kingdee.eas.fdc.tenancy.IOtherBillReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IOtherBillReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IOtherBillReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C8E6A5BC") ,com.kingdee.eas.fdc.tenancy.IOtherBillReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IOtherBillReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IOtherBillReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C8E6A5BC"));
    }
    public static com.kingdee.eas.fdc.tenancy.IOtherBillReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IOtherBillReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C8E6A5BC"));
    }
}