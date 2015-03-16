package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PayRequestSplitFactory
{
    private PayRequestSplitFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IPayRequestSplit getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayRequestSplit)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0A8C9DA4") ,com.kingdee.eas.fdc.finance.IPayRequestSplit.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IPayRequestSplit getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayRequestSplit)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0A8C9DA4") ,com.kingdee.eas.fdc.finance.IPayRequestSplit.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IPayRequestSplit getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayRequestSplit)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0A8C9DA4"));
    }
    public static com.kingdee.eas.fdc.finance.IPayRequestSplit getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayRequestSplit)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0A8C9DA4"));
    }
}