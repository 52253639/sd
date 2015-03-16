package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCProDepSplitUnConEntryFactory
{
    private FDCProDepSplitUnConEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepSplitUnConEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepSplitUnConEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5C10E3AB") ,com.kingdee.eas.fdc.finance.IFDCProDepSplitUnConEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCProDepSplitUnConEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepSplitUnConEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5C10E3AB") ,com.kingdee.eas.fdc.finance.IFDCProDepSplitUnConEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepSplitUnConEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepSplitUnConEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5C10E3AB"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepSplitUnConEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepSplitUnConEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5C10E3AB"));
    }
}