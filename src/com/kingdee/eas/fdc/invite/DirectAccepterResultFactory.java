package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DirectAccepterResultFactory
{
    private DirectAccepterResultFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IDirectAccepterResult getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IDirectAccepterResult)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7C775D9F") ,com.kingdee.eas.fdc.invite.IDirectAccepterResult.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IDirectAccepterResult getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IDirectAccepterResult)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7C775D9F") ,com.kingdee.eas.fdc.invite.IDirectAccepterResult.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IDirectAccepterResult getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IDirectAccepterResult)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7C775D9F"));
    }
    public static com.kingdee.eas.fdc.invite.IDirectAccepterResult getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IDirectAccepterResult)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7C775D9F"));
    }
}