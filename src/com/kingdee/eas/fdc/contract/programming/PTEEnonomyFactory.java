package com.kingdee.eas.fdc.contract.programming;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PTEEnonomyFactory
{
    private PTEEnonomyFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.programming.IPTEEnonomy getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IPTEEnonomy)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C4E9B822") ,com.kingdee.eas.fdc.contract.programming.IPTEEnonomy.class);
    }
    
    public static com.kingdee.eas.fdc.contract.programming.IPTEEnonomy getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IPTEEnonomy)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C4E9B822") ,com.kingdee.eas.fdc.contract.programming.IPTEEnonomy.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.programming.IPTEEnonomy getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IPTEEnonomy)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C4E9B822"));
    }
    public static com.kingdee.eas.fdc.contract.programming.IPTEEnonomy getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IPTEEnonomy)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C4E9B822"));
    }
}