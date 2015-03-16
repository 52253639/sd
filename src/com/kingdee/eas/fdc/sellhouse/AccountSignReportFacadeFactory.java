package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AccountSignReportFacadeFactory
{
    private AccountSignReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IAccountSignReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAccountSignReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EA33A05D") ,com.kingdee.eas.fdc.sellhouse.IAccountSignReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IAccountSignReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAccountSignReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EA33A05D") ,com.kingdee.eas.fdc.sellhouse.IAccountSignReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IAccountSignReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAccountSignReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EA33A05D"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IAccountSignReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAccountSignReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EA33A05D"));
    }
}