package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class NewListTempletColumnFactory
{
    private NewListTempletColumnFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.INewListTempletColumn getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListTempletColumn)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A2976F63") ,com.kingdee.eas.fdc.invite.INewListTempletColumn.class);
    }
    
    public static com.kingdee.eas.fdc.invite.INewListTempletColumn getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListTempletColumn)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A2976F63") ,com.kingdee.eas.fdc.invite.INewListTempletColumn.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.INewListTempletColumn getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListTempletColumn)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A2976F63"));
    }
    public static com.kingdee.eas.fdc.invite.INewListTempletColumn getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListTempletColumn)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A2976F63"));
    }
}