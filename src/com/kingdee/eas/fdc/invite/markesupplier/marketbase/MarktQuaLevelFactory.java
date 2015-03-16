package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarktQuaLevelFactory
{
    private MarktQuaLevelFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarktQuaLevel getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarktQuaLevel)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5C11D3A1") ,com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarktQuaLevel.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarktQuaLevel getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarktQuaLevel)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5C11D3A1") ,com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarktQuaLevel.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarktQuaLevel getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarktQuaLevel)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5C11D3A1"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarktQuaLevel getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarktQuaLevel)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5C11D3A1"));
    }
}