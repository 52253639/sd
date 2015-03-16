package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCProDepConPayContractEntryFactory
{
    private FDCProDepConPayContractEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepConPayContractEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepConPayContractEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1D7EE382") ,com.kingdee.eas.fdc.finance.IFDCProDepConPayContractEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCProDepConPayContractEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepConPayContractEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1D7EE382") ,com.kingdee.eas.fdc.finance.IFDCProDepConPayContractEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepConPayContractEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepConPayContractEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1D7EE382"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepConPayContractEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepConPayContractEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1D7EE382"));
    }
}