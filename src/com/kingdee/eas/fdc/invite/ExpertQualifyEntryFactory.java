package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ExpertQualifyEntryFactory
{
    private ExpertQualifyEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IExpertQualifyEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IExpertQualifyEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A13AB693") ,com.kingdee.eas.fdc.invite.IExpertQualifyEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IExpertQualifyEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IExpertQualifyEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A13AB693") ,com.kingdee.eas.fdc.invite.IExpertQualifyEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IExpertQualifyEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IExpertQualifyEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A13AB693"));
    }
    public static com.kingdee.eas.fdc.invite.IExpertQualifyEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IExpertQualifyEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A13AB693"));
    }
}