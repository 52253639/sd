package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WorkFlowReportFacadeFactory
{
    private WorkFlowReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IWorkFlowReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IWorkFlowReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BB901B28") ,com.kingdee.eas.fdc.contract.IWorkFlowReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IWorkFlowReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IWorkFlowReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BB901B28") ,com.kingdee.eas.fdc.contract.IWorkFlowReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IWorkFlowReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IWorkFlowReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BB901B28"));
    }
    public static com.kingdee.eas.fdc.contract.IWorkFlowReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IWorkFlowReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BB901B28"));
    }
}