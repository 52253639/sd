package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ListingItemFactory
{
    private ListingItemFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IListingItem getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IListingItem)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F200A613") ,com.kingdee.eas.fdc.invite.IListingItem.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IListingItem getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IListingItem)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F200A613") ,com.kingdee.eas.fdc.invite.IListingItem.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IListingItem getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IListingItem)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F200A613"));
    }
    public static com.kingdee.eas.fdc.invite.IListingItem getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IListingItem)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F200A613"));
    }
}