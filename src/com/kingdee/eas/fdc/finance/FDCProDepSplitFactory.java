package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCProDepSplitFactory
{
    private FDCProDepSplitFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepSplit getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepSplit)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A544F042") ,com.kingdee.eas.fdc.finance.IFDCProDepSplit.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCProDepSplit getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepSplit)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A544F042") ,com.kingdee.eas.fdc.finance.IFDCProDepSplit.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepSplit getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepSplit)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A544F042"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepSplit getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepSplit)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A544F042"));
    }
}