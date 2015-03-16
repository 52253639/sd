package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteCalibrationFactory
{
    private InviteCalibrationFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteCalibration getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteCalibration)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E9172C7D") ,com.kingdee.eas.fdc.invite.IInviteCalibration.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteCalibration getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteCalibration)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E9172C7D") ,com.kingdee.eas.fdc.invite.IInviteCalibration.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteCalibration getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteCalibration)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E9172C7D"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteCalibration getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteCalibration)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E9172C7D"));
    }
}