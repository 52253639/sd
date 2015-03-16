package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class NewListTempletPageFactory
{
    private NewListTempletPageFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.INewListTempletPage getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListTempletPage)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("492E9DDC") ,com.kingdee.eas.fdc.invite.INewListTempletPage.class);
    }
    
    public static com.kingdee.eas.fdc.invite.INewListTempletPage getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListTempletPage)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("492E9DDC") ,com.kingdee.eas.fdc.invite.INewListTempletPage.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.INewListTempletPage getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListTempletPage)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("492E9DDC"));
    }
    public static com.kingdee.eas.fdc.invite.INewListTempletPage getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListTempletPage)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("492E9DDC"));
    }
}