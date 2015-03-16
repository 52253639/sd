package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AppraiseGuideLineTypeFactory
{
    private AppraiseGuideLineTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IAppraiseGuideLineType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAppraiseGuideLineType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("11DFF31B") ,com.kingdee.eas.fdc.invite.IAppraiseGuideLineType.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IAppraiseGuideLineType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAppraiseGuideLineType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("11DFF31B") ,com.kingdee.eas.fdc.invite.IAppraiseGuideLineType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IAppraiseGuideLineType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAppraiseGuideLineType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("11DFF31B"));
    }
    public static com.kingdee.eas.fdc.invite.IAppraiseGuideLineType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAppraiseGuideLineType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("11DFF31B"));
    }
}