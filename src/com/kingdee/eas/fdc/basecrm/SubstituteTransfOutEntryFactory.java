package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SubstituteTransfOutEntryFactory
{
    private SubstituteTransfOutEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.basecrm.ISubstituteTransfOutEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ISubstituteTransfOutEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EC719538") ,com.kingdee.eas.fdc.basecrm.ISubstituteTransfOutEntry.class);
    }
    
    public static com.kingdee.eas.fdc.basecrm.ISubstituteTransfOutEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ISubstituteTransfOutEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EC719538") ,com.kingdee.eas.fdc.basecrm.ISubstituteTransfOutEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basecrm.ISubstituteTransfOutEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ISubstituteTransfOutEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EC719538"));
    }
    public static com.kingdee.eas.fdc.basecrm.ISubstituteTransfOutEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ISubstituteTransfOutEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EC719538"));
    }
}