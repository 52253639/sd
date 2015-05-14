package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OrgUnitMonthPGReportFacadeFactory
{
    private OrgUnitMonthPGReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IOrgUnitMonthPGReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IOrgUnitMonthPGReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9F5AE1AC") ,com.kingdee.eas.fdc.finance.IOrgUnitMonthPGReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IOrgUnitMonthPGReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IOrgUnitMonthPGReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9F5AE1AC") ,com.kingdee.eas.fdc.finance.IOrgUnitMonthPGReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IOrgUnitMonthPGReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IOrgUnitMonthPGReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9F5AE1AC"));
    }
    public static com.kingdee.eas.fdc.finance.IOrgUnitMonthPGReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IOrgUnitMonthPGReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9F5AE1AC"));
    }
}