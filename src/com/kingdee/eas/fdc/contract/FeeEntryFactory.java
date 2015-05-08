package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FeeEntryFactory
{
    private FeeEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IFeeEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IFeeEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6496E7C7") ,com.kingdee.eas.fdc.contract.IFeeEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IFeeEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IFeeEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6496E7C7") ,com.kingdee.eas.fdc.contract.IFeeEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IFeeEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IFeeEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6496E7C7"));
    }
    public static com.kingdee.eas.fdc.contract.IFeeEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IFeeEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6496E7C7"));
    }
}