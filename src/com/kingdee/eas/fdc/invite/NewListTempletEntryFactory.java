package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class NewListTempletEntryFactory
{
    private NewListTempletEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.INewListTempletEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListTempletEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DC1036C5") ,com.kingdee.eas.fdc.invite.INewListTempletEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.INewListTempletEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListTempletEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DC1036C5") ,com.kingdee.eas.fdc.invite.INewListTempletEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.INewListTempletEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListTempletEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DC1036C5"));
    }
    public static com.kingdee.eas.fdc.invite.INewListTempletEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListTempletEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DC1036C5"));
    }
}