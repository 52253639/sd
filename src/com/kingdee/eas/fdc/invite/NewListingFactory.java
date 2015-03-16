package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class NewListingFactory
{
    private NewListingFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.INewListing getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListing)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("457A7AE8") ,com.kingdee.eas.fdc.invite.INewListing.class);
    }
    
    public static com.kingdee.eas.fdc.invite.INewListing getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListing)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("457A7AE8") ,com.kingdee.eas.fdc.invite.INewListing.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.INewListing getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListing)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("457A7AE8"));
    }
    public static com.kingdee.eas.fdc.invite.INewListing getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListing)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("457A7AE8"));
    }
}