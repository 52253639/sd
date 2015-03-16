package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SinObligateAttachEntryFactory
{
    private SinObligateAttachEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ISinObligateAttachEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ISinObligateAttachEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B99A6FC3") ,com.kingdee.eas.fdc.tenancy.ISinObligateAttachEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ISinObligateAttachEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ISinObligateAttachEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B99A6FC3") ,com.kingdee.eas.fdc.tenancy.ISinObligateAttachEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ISinObligateAttachEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ISinObligateAttachEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B99A6FC3"));
    }
    public static com.kingdee.eas.fdc.tenancy.ISinObligateAttachEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ISinObligateAttachEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B99A6FC3"));
    }
}