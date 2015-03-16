package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ListingPageSumEntryFactory
{
    private ListingPageSumEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IListingPageSumEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IListingPageSumEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5B9B8396") ,com.kingdee.eas.fdc.invite.IListingPageSumEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IListingPageSumEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IListingPageSumEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5B9B8396") ,com.kingdee.eas.fdc.invite.IListingPageSumEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IListingPageSumEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IListingPageSumEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5B9B8396"));
    }
    public static com.kingdee.eas.fdc.invite.IListingPageSumEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IListingPageSumEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5B9B8396"));
    }
}