package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCProDepSplitUnConFactory
{
    private FDCProDepSplitUnConFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepSplitUnCon getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepSplitUnCon)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("05D1C107") ,com.kingdee.eas.fdc.finance.IFDCProDepSplitUnCon.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCProDepSplitUnCon getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepSplitUnCon)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("05D1C107") ,com.kingdee.eas.fdc.finance.IFDCProDepSplitUnCon.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepSplitUnCon getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepSplitUnCon)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("05D1C107"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepSplitUnCon getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepSplitUnCon)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("05D1C107"));
    }
}