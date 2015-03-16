package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TenancyBillFacadeFactory
{
    private TenancyBillFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyBillFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyBillFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("CA3F5C18") ,com.kingdee.eas.fdc.tenancy.ITenancyBillFacade.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ITenancyBillFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyBillFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("CA3F5C18") ,com.kingdee.eas.fdc.tenancy.ITenancyBillFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyBillFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyBillFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("CA3F5C18"));
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyBillFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyBillFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("CA3F5C18"));
    }
}