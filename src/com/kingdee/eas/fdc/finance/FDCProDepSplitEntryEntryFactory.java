package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCProDepSplitEntryEntryFactory
{
    private FDCProDepSplitEntryEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepSplitEntryEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepSplitEntryEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("AB0B6902") ,com.kingdee.eas.fdc.finance.IFDCProDepSplitEntryEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCProDepSplitEntryEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepSplitEntryEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("AB0B6902") ,com.kingdee.eas.fdc.finance.IFDCProDepSplitEntryEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepSplitEntryEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepSplitEntryEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("AB0B6902"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepSplitEntryEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepSplitEntryEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("AB0B6902"));
    }
}