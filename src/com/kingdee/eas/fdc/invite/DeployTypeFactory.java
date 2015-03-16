package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DeployTypeFactory
{
    private DeployTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IDeployType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IDeployType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8ABE8EE5") ,com.kingdee.eas.fdc.invite.IDeployType.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IDeployType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IDeployType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8ABE8EE5") ,com.kingdee.eas.fdc.invite.IDeployType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IDeployType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IDeployType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8ABE8EE5"));
    }
    public static com.kingdee.eas.fdc.invite.IDeployType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IDeployType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8ABE8EE5"));
    }
}