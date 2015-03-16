package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketGradeSetUpFactory
{
    private MarketGradeSetUpFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketGradeSetUp getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketGradeSetUp)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4C48410F") ,com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketGradeSetUp.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketGradeSetUp getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketGradeSetUp)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4C48410F") ,com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketGradeSetUp.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketGradeSetUp getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketGradeSetUp)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4C48410F"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketGradeSetUp getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketGradeSetUp)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4C48410F"));
    }
}