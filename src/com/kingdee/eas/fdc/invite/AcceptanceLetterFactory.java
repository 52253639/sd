package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AcceptanceLetterFactory
{
    private AcceptanceLetterFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IAcceptanceLetter getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAcceptanceLetter)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1DD52081") ,com.kingdee.eas.fdc.invite.IAcceptanceLetter.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IAcceptanceLetter getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAcceptanceLetter)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1DD52081") ,com.kingdee.eas.fdc.invite.IAcceptanceLetter.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IAcceptanceLetter getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAcceptanceLetter)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1DD52081"));
    }
    public static com.kingdee.eas.fdc.invite.IAcceptanceLetter getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAcceptanceLetter)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1DD52081"));
    }
}