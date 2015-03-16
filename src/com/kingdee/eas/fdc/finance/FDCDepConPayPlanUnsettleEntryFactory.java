package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCDepConPayPlanUnsettleEntryFactory
{
    private FDCDepConPayPlanUnsettleEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanUnsettleEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanUnsettleEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("08EEA566") ,com.kingdee.eas.fdc.finance.IFDCDepConPayPlanUnsettleEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanUnsettleEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanUnsettleEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("08EEA566") ,com.kingdee.eas.fdc.finance.IFDCDepConPayPlanUnsettleEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanUnsettleEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanUnsettleEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("08EEA566"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepConPayPlanUnsettleEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepConPayPlanUnsettleEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("08EEA566"));
    }
}