package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractBillPayPlanReportFacadeFactory
{
    private ContractBillPayPlanReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IContractBillPayPlanReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IContractBillPayPlanReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DEFA9597") ,com.kingdee.eas.fdc.finance.IContractBillPayPlanReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IContractBillPayPlanReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IContractBillPayPlanReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DEFA9597") ,com.kingdee.eas.fdc.finance.IContractBillPayPlanReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IContractBillPayPlanReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IContractBillPayPlanReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DEFA9597"));
    }
    public static com.kingdee.eas.fdc.finance.IContractBillPayPlanReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IContractBillPayPlanReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DEFA9597"));
    }
}