package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class NewListTempletValueFactory
{
    private NewListTempletValueFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.INewListTempletValue getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListTempletValue)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DCF9BFC4") ,com.kingdee.eas.fdc.invite.INewListTempletValue.class);
    }
    
    public static com.kingdee.eas.fdc.invite.INewListTempletValue getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListTempletValue)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DCF9BFC4") ,com.kingdee.eas.fdc.invite.INewListTempletValue.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.INewListTempletValue getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListTempletValue)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DCF9BFC4"));
    }
    public static com.kingdee.eas.fdc.invite.INewListTempletValue getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListTempletValue)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DCF9BFC4"));
    }
}