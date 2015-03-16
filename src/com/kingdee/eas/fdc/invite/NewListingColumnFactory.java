package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class NewListingColumnFactory
{
    private NewListingColumnFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.INewListingColumn getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListingColumn)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E3806C5E") ,com.kingdee.eas.fdc.invite.INewListingColumn.class);
    }
    
    public static com.kingdee.eas.fdc.invite.INewListingColumn getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListingColumn)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E3806C5E") ,com.kingdee.eas.fdc.invite.INewListingColumn.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.INewListingColumn getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListingColumn)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E3806C5E"));
    }
    public static com.kingdee.eas.fdc.invite.INewListingColumn getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListingColumn)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E3806C5E"));
    }
}