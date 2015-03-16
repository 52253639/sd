package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SubstituteAdjustFactory
{
    private SubstituteAdjustFactory()
    {
    }
    public static com.kingdee.eas.fdc.basecrm.ISubstituteAdjust getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ISubstituteAdjust)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F552D025") ,com.kingdee.eas.fdc.basecrm.ISubstituteAdjust.class);
    }
    
    public static com.kingdee.eas.fdc.basecrm.ISubstituteAdjust getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ISubstituteAdjust)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F552D025") ,com.kingdee.eas.fdc.basecrm.ISubstituteAdjust.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basecrm.ISubstituteAdjust getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ISubstituteAdjust)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F552D025"));
    }
    public static com.kingdee.eas.fdc.basecrm.ISubstituteAdjust getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ISubstituteAdjust)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F552D025"));
    }
}