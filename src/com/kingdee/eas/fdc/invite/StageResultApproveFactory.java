package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class StageResultApproveFactory
{
    private StageResultApproveFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IStageResultApprove getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IStageResultApprove)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BF4D2436") ,com.kingdee.eas.fdc.invite.IStageResultApprove.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IStageResultApprove getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IStageResultApprove)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BF4D2436") ,com.kingdee.eas.fdc.invite.IStageResultApprove.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IStageResultApprove getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IStageResultApprove)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BF4D2436"));
    }
    public static com.kingdee.eas.fdc.invite.IStageResultApprove getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IStageResultApprove)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BF4D2436"));
    }
}