package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WeiChatFacadeFactory
{
    private WeiChatFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IWeiChatFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IWeiChatFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2CD1C8C4") ,com.kingdee.eas.fdc.tenancy.IWeiChatFacade.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IWeiChatFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IWeiChatFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2CD1C8C4") ,com.kingdee.eas.fdc.tenancy.IWeiChatFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IWeiChatFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IWeiChatFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2CD1C8C4"));
    }
    public static com.kingdee.eas.fdc.tenancy.IWeiChatFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IWeiChatFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2CD1C8C4"));
    }
}