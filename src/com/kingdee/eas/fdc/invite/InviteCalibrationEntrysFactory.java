package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteCalibrationEntrysFactory
{
    private InviteCalibrationEntrysFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteCalibrationEntrys getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteCalibrationEntrys)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5E740D1E") ,com.kingdee.eas.fdc.invite.IInviteCalibrationEntrys.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteCalibrationEntrys getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteCalibrationEntrys)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5E740D1E") ,com.kingdee.eas.fdc.invite.IInviteCalibrationEntrys.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteCalibrationEntrys getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteCalibrationEntrys)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5E740D1E"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteCalibrationEntrys getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteCalibrationEntrys)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5E740D1E"));
    }
}