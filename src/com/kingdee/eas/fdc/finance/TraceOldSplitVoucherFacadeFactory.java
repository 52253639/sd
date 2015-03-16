package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TraceOldSplitVoucherFacadeFactory
{
    private TraceOldSplitVoucherFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.ITraceOldSplitVoucherFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.ITraceOldSplitVoucherFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3D68BA7F") ,com.kingdee.eas.fdc.finance.ITraceOldSplitVoucherFacade.class);
    }
    
    public static com.kingdee.eas.fdc.finance.ITraceOldSplitVoucherFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.ITraceOldSplitVoucherFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3D68BA7F") ,com.kingdee.eas.fdc.finance.ITraceOldSplitVoucherFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.ITraceOldSplitVoucherFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.ITraceOldSplitVoucherFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3D68BA7F"));
    }
    public static com.kingdee.eas.fdc.finance.ITraceOldSplitVoucherFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.ITraceOldSplitVoucherFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3D68BA7F"));
    }
}