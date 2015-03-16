package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCProDepSplitEntryFactory
{
    private FDCProDepSplitEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepSplitEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepSplitEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("04F10150") ,com.kingdee.eas.fdc.finance.IFDCProDepSplitEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCProDepSplitEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepSplitEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("04F10150") ,com.kingdee.eas.fdc.finance.IFDCProDepSplitEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepSplitEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepSplitEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("04F10150"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepSplitEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepSplitEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("04F10150"));
    }
}