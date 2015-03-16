package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCProDepSplitHasConFactory
{
    private FDCProDepSplitHasConFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepSplitHasCon getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepSplitHasCon)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9D955B6A") ,com.kingdee.eas.fdc.finance.IFDCProDepSplitHasCon.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCProDepSplitHasCon getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepSplitHasCon)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9D955B6A") ,com.kingdee.eas.fdc.finance.IFDCProDepSplitHasCon.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepSplitHasCon getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepSplitHasCon)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9D955B6A"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepSplitHasCon getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepSplitHasCon)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9D955B6A"));
    }
}