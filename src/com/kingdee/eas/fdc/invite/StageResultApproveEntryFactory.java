package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class StageResultApproveEntryFactory
{
    private StageResultApproveEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IStageResultApproveEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IStageResultApproveEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("976F25DC") ,com.kingdee.eas.fdc.invite.IStageResultApproveEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IStageResultApproveEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IStageResultApproveEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("976F25DC") ,com.kingdee.eas.fdc.invite.IStageResultApproveEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IStageResultApproveEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IStageResultApproveEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("976F25DC"));
    }
    public static com.kingdee.eas.fdc.invite.IStageResultApproveEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IStageResultApproveEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("976F25DC"));
    }
}