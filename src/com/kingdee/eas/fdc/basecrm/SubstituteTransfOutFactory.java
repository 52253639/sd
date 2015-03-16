package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SubstituteTransfOutFactory
{
    private SubstituteTransfOutFactory()
    {
    }
    public static com.kingdee.eas.fdc.basecrm.ISubstituteTransfOut getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ISubstituteTransfOut)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("57618B5A") ,com.kingdee.eas.fdc.basecrm.ISubstituteTransfOut.class);
    }
    
    public static com.kingdee.eas.fdc.basecrm.ISubstituteTransfOut getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ISubstituteTransfOut)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("57618B5A") ,com.kingdee.eas.fdc.basecrm.ISubstituteTransfOut.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basecrm.ISubstituteTransfOut getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ISubstituteTransfOut)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("57618B5A"));
    }
    public static com.kingdee.eas.fdc.basecrm.ISubstituteTransfOut getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ISubstituteTransfOut)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("57618B5A"));
    }
}