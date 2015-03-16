package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RefPriceFactory
{
    private RefPriceFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IRefPrice getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IRefPrice)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("51008B9A") ,com.kingdee.eas.fdc.invite.IRefPrice.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IRefPrice getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IRefPrice)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("51008B9A") ,com.kingdee.eas.fdc.invite.IRefPrice.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IRefPrice getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IRefPrice)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("51008B9A"));
    }
    public static com.kingdee.eas.fdc.invite.IRefPrice getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IRefPrice)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("51008B9A"));
    }
}