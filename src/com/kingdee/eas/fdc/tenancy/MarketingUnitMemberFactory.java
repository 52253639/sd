package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketingUnitMemberFactory
{
    private MarketingUnitMemberFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IMarketingUnitMember getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IMarketingUnitMember)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E155FFBB") ,com.kingdee.eas.fdc.tenancy.IMarketingUnitMember.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IMarketingUnitMember getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IMarketingUnitMember)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E155FFBB") ,com.kingdee.eas.fdc.tenancy.IMarketingUnitMember.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IMarketingUnitMember getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IMarketingUnitMember)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E155FFBB"));
    }
    public static com.kingdee.eas.fdc.tenancy.IMarketingUnitMember getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IMarketingUnitMember)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E155FFBB"));
    }
}