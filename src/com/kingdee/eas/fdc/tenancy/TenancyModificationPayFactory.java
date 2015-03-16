package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TenancyModificationPayFactory
{
    private TenancyModificationPayFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyModificationPay getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyModificationPay)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("22C9FB35") ,com.kingdee.eas.fdc.tenancy.ITenancyModificationPay.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ITenancyModificationPay getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyModificationPay)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("22C9FB35") ,com.kingdee.eas.fdc.tenancy.ITenancyModificationPay.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyModificationPay getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyModificationPay)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("22C9FB35"));
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyModificationPay getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyModificationPay)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("22C9FB35"));
    }
}