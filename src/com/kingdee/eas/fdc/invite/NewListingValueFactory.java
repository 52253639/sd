package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class NewListingValueFactory
{
    private NewListingValueFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.INewListingValue getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListingValue)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D6CFB769") ,com.kingdee.eas.fdc.invite.INewListingValue.class);
    }
    
    public static com.kingdee.eas.fdc.invite.INewListingValue getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListingValue)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D6CFB769") ,com.kingdee.eas.fdc.invite.INewListingValue.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.INewListingValue getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListingValue)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D6CFB769"));
    }
    public static com.kingdee.eas.fdc.invite.INewListingValue getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListingValue)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D6CFB769"));
    }
}