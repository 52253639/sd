package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InvestorHouseLinkmanEntrysFactory
{
    private InvestorHouseLinkmanEntrysFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IInvestorHouseLinkmanEntrys getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IInvestorHouseLinkmanEntrys)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("72085814") ,com.kingdee.eas.fdc.sellhouse.IInvestorHouseLinkmanEntrys.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IInvestorHouseLinkmanEntrys getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IInvestorHouseLinkmanEntrys)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("72085814") ,com.kingdee.eas.fdc.sellhouse.IInvestorHouseLinkmanEntrys.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IInvestorHouseLinkmanEntrys getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IInvestorHouseLinkmanEntrys)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("72085814"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IInvestorHouseLinkmanEntrys getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IInvestorHouseLinkmanEntrys)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("72085814"));
    }
}