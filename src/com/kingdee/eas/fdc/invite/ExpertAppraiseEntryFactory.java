package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ExpertAppraiseEntryFactory
{
    private ExpertAppraiseEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IExpertAppraiseEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IExpertAppraiseEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7703E119") ,com.kingdee.eas.fdc.invite.IExpertAppraiseEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IExpertAppraiseEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IExpertAppraiseEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7703E119") ,com.kingdee.eas.fdc.invite.IExpertAppraiseEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IExpertAppraiseEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IExpertAppraiseEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7703E119"));
    }
    public static com.kingdee.eas.fdc.invite.IExpertAppraiseEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IExpertAppraiseEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7703E119"));
    }
}