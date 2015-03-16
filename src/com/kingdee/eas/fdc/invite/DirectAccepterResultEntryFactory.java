package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DirectAccepterResultEntryFactory
{
    private DirectAccepterResultEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IDirectAccepterResultEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IDirectAccepterResultEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7BED6613") ,com.kingdee.eas.fdc.invite.IDirectAccepterResultEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IDirectAccepterResultEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IDirectAccepterResultEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7BED6613") ,com.kingdee.eas.fdc.invite.IDirectAccepterResultEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IDirectAccepterResultEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IDirectAccepterResultEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7BED6613"));
    }
    public static com.kingdee.eas.fdc.invite.IDirectAccepterResultEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IDirectAccepterResultEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7BED6613"));
    }
}