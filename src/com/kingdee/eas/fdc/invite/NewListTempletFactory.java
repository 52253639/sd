package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class NewListTempletFactory
{
    private NewListTempletFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.INewListTemplet getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListTemplet)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("56BD65AD") ,com.kingdee.eas.fdc.invite.INewListTemplet.class);
    }
    
    public static com.kingdee.eas.fdc.invite.INewListTemplet getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListTemplet)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("56BD65AD") ,com.kingdee.eas.fdc.invite.INewListTemplet.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.INewListTemplet getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListTemplet)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("56BD65AD"));
    }
    public static com.kingdee.eas.fdc.invite.INewListTemplet getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListTemplet)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("56BD65AD"));
    }
}