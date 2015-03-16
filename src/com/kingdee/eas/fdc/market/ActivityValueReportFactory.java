package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ActivityValueReportFactory
{
    private ActivityValueReportFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IActivityValueReport getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IActivityValueReport)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("83D03E05") ,com.kingdee.eas.fdc.market.IActivityValueReport.class);
    }
    
    public static com.kingdee.eas.fdc.market.IActivityValueReport getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IActivityValueReport)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("83D03E05") ,com.kingdee.eas.fdc.market.IActivityValueReport.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IActivityValueReport getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IActivityValueReport)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("83D03E05"));
    }
    public static com.kingdee.eas.fdc.market.IActivityValueReport getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IActivityValueReport)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("83D03E05"));
    }
}