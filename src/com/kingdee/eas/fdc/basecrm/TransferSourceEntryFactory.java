package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TransferSourceEntryFactory
{
    private TransferSourceEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.basecrm.ITransferSourceEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ITransferSourceEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DCBE7750") ,com.kingdee.eas.fdc.basecrm.ITransferSourceEntry.class);
    }
    
    public static com.kingdee.eas.fdc.basecrm.ITransferSourceEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ITransferSourceEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DCBE7750") ,com.kingdee.eas.fdc.basecrm.ITransferSourceEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basecrm.ITransferSourceEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ITransferSourceEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DCBE7750"));
    }
    public static com.kingdee.eas.fdc.basecrm.ITransferSourceEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ITransferSourceEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DCBE7750"));
    }
}