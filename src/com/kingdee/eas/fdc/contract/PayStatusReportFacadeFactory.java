package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PayStatusReportFacadeFactory
{
    private PayStatusReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IPayStatusReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPayStatusReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3200576D") ,com.kingdee.eas.fdc.contract.IPayStatusReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IPayStatusReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPayStatusReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3200576D") ,com.kingdee.eas.fdc.contract.IPayStatusReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IPayStatusReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPayStatusReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3200576D"));
    }
    public static com.kingdee.eas.fdc.contract.IPayStatusReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPayStatusReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3200576D"));
    }
}