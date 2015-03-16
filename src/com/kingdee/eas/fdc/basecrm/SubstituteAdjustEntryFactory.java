package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SubstituteAdjustEntryFactory
{
    private SubstituteAdjustEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.basecrm.ISubstituteAdjustEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ISubstituteAdjustEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C112974D") ,com.kingdee.eas.fdc.basecrm.ISubstituteAdjustEntry.class);
    }
    
    public static com.kingdee.eas.fdc.basecrm.ISubstituteAdjustEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ISubstituteAdjustEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C112974D") ,com.kingdee.eas.fdc.basecrm.ISubstituteAdjustEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basecrm.ISubstituteAdjustEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ISubstituteAdjustEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C112974D"));
    }
    public static com.kingdee.eas.fdc.basecrm.ISubstituteAdjustEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ISubstituteAdjustEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C112974D"));
    }
}