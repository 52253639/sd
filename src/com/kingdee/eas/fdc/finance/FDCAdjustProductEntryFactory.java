package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCAdjustProductEntryFactory
{
    private FDCAdjustProductEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCAdjustProductEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCAdjustProductEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2AEE5488") ,com.kingdee.eas.fdc.finance.IFDCAdjustProductEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCAdjustProductEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCAdjustProductEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2AEE5488") ,com.kingdee.eas.fdc.finance.IFDCAdjustProductEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCAdjustProductEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCAdjustProductEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2AEE5488"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCAdjustProductEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCAdjustProductEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2AEE5488"));
    }
}