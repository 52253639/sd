package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InvestorHouseTrackRecordFactory
{
    private InvestorHouseTrackRecordFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IInvestorHouseTrackRecord getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IInvestorHouseTrackRecord)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E2FB70CF") ,com.kingdee.eas.fdc.sellhouse.IInvestorHouseTrackRecord.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IInvestorHouseTrackRecord getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IInvestorHouseTrackRecord)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E2FB70CF") ,com.kingdee.eas.fdc.sellhouse.IInvestorHouseTrackRecord.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IInvestorHouseTrackRecord getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IInvestorHouseTrackRecord)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E2FB70CF"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IInvestorHouseTrackRecord getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IInvestorHouseTrackRecord)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E2FB70CF"));
    }
}