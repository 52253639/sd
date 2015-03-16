package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketingUnitMemberFactory
{
    private MarketingUnitMemberFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IMarketingUnitMember getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMarketingUnitMember)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("13D50949") ,com.kingdee.eas.fdc.sellhouse.IMarketingUnitMember.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IMarketingUnitMember getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMarketingUnitMember)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("13D50949") ,com.kingdee.eas.fdc.sellhouse.IMarketingUnitMember.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IMarketingUnitMember getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMarketingUnitMember)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("13D50949"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IMarketingUnitMember getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMarketingUnitMember)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("13D50949"));
    }
}