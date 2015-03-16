package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AccountReportFacadeFactory
{
    private AccountReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IAccountReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAccountReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("48FC7B00") ,com.kingdee.eas.fdc.sellhouse.IAccountReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IAccountReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAccountReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("48FC7B00") ,com.kingdee.eas.fdc.sellhouse.IAccountReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IAccountReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAccountReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("48FC7B00"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IAccountReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAccountReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("48FC7B00"));
    }
}