package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class VisibilityFactory
{
    private VisibilityFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IVisibility getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IVisibility)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8E5B3F7F") ,com.kingdee.eas.fdc.invite.markesupplier.marketbase.IVisibility.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IVisibility getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IVisibility)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8E5B3F7F") ,com.kingdee.eas.fdc.invite.markesupplier.marketbase.IVisibility.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IVisibility getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IVisibility)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8E5B3F7F"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IVisibility getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IVisibility)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8E5B3F7F"));
    }
}