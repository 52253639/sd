package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AppraiseGuideLineFactory
{
    private AppraiseGuideLineFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IAppraiseGuideLine getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAppraiseGuideLine)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EEDFF841") ,com.kingdee.eas.fdc.invite.IAppraiseGuideLine.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IAppraiseGuideLine getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAppraiseGuideLine)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EEDFF841") ,com.kingdee.eas.fdc.invite.IAppraiseGuideLine.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IAppraiseGuideLine getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAppraiseGuideLine)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EEDFF841"));
    }
    public static com.kingdee.eas.fdc.invite.IAppraiseGuideLine getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAppraiseGuideLine)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EEDFF841"));
    }
}