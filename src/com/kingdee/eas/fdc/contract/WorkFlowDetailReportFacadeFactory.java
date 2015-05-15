package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WorkFlowDetailReportFacadeFactory
{
    private WorkFlowDetailReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IWorkFlowDetailReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IWorkFlowDetailReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4B580299") ,com.kingdee.eas.fdc.contract.IWorkFlowDetailReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IWorkFlowDetailReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IWorkFlowDetailReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4B580299") ,com.kingdee.eas.fdc.contract.IWorkFlowDetailReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IWorkFlowDetailReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IWorkFlowDetailReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4B580299"));
    }
    public static com.kingdee.eas.fdc.contract.IWorkFlowDetailReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IWorkFlowDetailReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4B580299"));
    }
}