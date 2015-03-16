package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DrawingDepthFactory
{
    private DrawingDepthFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IDrawingDepth getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IDrawingDepth)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DBE77669") ,com.kingdee.eas.fdc.invite.IDrawingDepth.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IDrawingDepth getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IDrawingDepth)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DBE77669") ,com.kingdee.eas.fdc.invite.IDrawingDepth.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IDrawingDepth getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IDrawingDepth)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DBE77669"));
    }
    public static com.kingdee.eas.fdc.invite.IDrawingDepth getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IDrawingDepth)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DBE77669"));
    }
}