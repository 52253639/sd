package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SHERevMapFactory
{
    private SHERevMapFactory()
    {
    }
    public static com.kingdee.eas.fdc.basecrm.ISHERevMap getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ISHERevMap)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("AC615AAD") ,com.kingdee.eas.fdc.basecrm.ISHERevMap.class);
    }
    
    public static com.kingdee.eas.fdc.basecrm.ISHERevMap getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ISHERevMap)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("AC615AAD") ,com.kingdee.eas.fdc.basecrm.ISHERevMap.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basecrm.ISHERevMap getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ISHERevMap)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("AC615AAD"));
    }
    public static com.kingdee.eas.fdc.basecrm.ISHERevMap getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ISHERevMap)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("AC615AAD"));
    }
}