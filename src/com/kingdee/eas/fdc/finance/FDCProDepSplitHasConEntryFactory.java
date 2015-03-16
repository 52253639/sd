package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCProDepSplitHasConEntryFactory
{
    private FDCProDepSplitHasConEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepSplitHasConEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepSplitHasConEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("996D4F28") ,com.kingdee.eas.fdc.finance.IFDCProDepSplitHasConEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCProDepSplitHasConEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepSplitHasConEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("996D4F28") ,com.kingdee.eas.fdc.finance.IFDCProDepSplitHasConEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepSplitHasConEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepSplitHasConEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("996D4F28"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepSplitHasConEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepSplitHasConEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("996D4F28"));
    }
}