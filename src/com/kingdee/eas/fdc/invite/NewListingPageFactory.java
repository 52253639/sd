package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class NewListingPageFactory
{
    private NewListingPageFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.INewListingPage getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListingPage)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BC989D97") ,com.kingdee.eas.fdc.invite.INewListingPage.class);
    }
    
    public static com.kingdee.eas.fdc.invite.INewListingPage getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListingPage)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BC989D97") ,com.kingdee.eas.fdc.invite.INewListingPage.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.INewListingPage getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListingPage)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BC989D97"));
    }
    public static com.kingdee.eas.fdc.invite.INewListingPage getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListingPage)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BC989D97"));
    }
}