package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ShareSellerFactory
{
    private ShareSellerFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IShareSeller getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IShareSeller)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3E9F3A83") ,com.kingdee.eas.fdc.sellhouse.IShareSeller.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IShareSeller getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IShareSeller)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3E9F3A83") ,com.kingdee.eas.fdc.sellhouse.IShareSeller.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IShareSeller getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IShareSeller)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3E9F3A83"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IShareSeller getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IShareSeller)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3E9F3A83"));
    }
}