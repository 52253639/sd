package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCProDepSplitNoConEntryFactory
{
    private FDCProDepSplitNoConEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepSplitNoConEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepSplitNoConEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("42533AD3") ,com.kingdee.eas.fdc.finance.IFDCProDepSplitNoConEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCProDepSplitNoConEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepSplitNoConEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("42533AD3") ,com.kingdee.eas.fdc.finance.IFDCProDepSplitNoConEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepSplitNoConEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepSplitNoConEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("42533AD3"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepSplitNoConEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepSplitNoConEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("42533AD3"));
    }
}