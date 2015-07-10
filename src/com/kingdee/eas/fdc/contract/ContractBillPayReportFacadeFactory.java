package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractBillPayReportFacadeFactory
{
    private ContractBillPayReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractBillPayReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillPayReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5C26C322") ,com.kingdee.eas.fdc.contract.IContractBillPayReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractBillPayReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillPayReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5C26C322") ,com.kingdee.eas.fdc.contract.IContractBillPayReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractBillPayReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillPayReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5C26C322"));
    }
    public static com.kingdee.eas.fdc.contract.IContractBillPayReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillPayReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5C26C322"));
    }
}