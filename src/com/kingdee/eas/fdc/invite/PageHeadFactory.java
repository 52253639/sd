package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PageHeadFactory
{
    private PageHeadFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IPageHead getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IPageHead)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B06EE433") ,com.kingdee.eas.fdc.invite.IPageHead.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IPageHead getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IPageHead)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B06EE433") ,com.kingdee.eas.fdc.invite.IPageHead.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IPageHead getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IPageHead)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B06EE433"));
    }
    public static com.kingdee.eas.fdc.invite.IPageHead getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IPageHead)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B06EE433"));
    }
}