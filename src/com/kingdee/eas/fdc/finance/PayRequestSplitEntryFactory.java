package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PayRequestSplitEntryFactory
{
    private PayRequestSplitEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IPayRequestSplitEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayRequestSplitEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B1A8612E") ,com.kingdee.eas.fdc.finance.IPayRequestSplitEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IPayRequestSplitEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayRequestSplitEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B1A8612E") ,com.kingdee.eas.fdc.finance.IPayRequestSplitEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IPayRequestSplitEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayRequestSplitEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B1A8612E"));
    }
    public static com.kingdee.eas.fdc.finance.IPayRequestSplitEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayRequestSplitEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B1A8612E"));
    }
}