package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class NewListingEntryFactory
{
    private NewListingEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.INewListingEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListingEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D5E62E6A") ,com.kingdee.eas.fdc.invite.INewListingEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.INewListingEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListingEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D5E62E6A") ,com.kingdee.eas.fdc.invite.INewListingEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.INewListingEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListingEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D5E62E6A"));
    }
    public static com.kingdee.eas.fdc.invite.INewListingEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListingEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D5E62E6A"));
    }
}