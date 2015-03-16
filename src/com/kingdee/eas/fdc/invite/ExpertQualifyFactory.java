package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ExpertQualifyFactory
{
    private ExpertQualifyFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IExpertQualify getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IExpertQualify)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B739BD1F") ,com.kingdee.eas.fdc.invite.IExpertQualify.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IExpertQualify getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IExpertQualify)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B739BD1F") ,com.kingdee.eas.fdc.invite.IExpertQualify.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IExpertQualify getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IExpertQualify)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B739BD1F"));
    }
    public static com.kingdee.eas.fdc.invite.IExpertQualify getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IExpertQualify)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B739BD1F"));
    }
}