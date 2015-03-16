package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SharePropertyFactory
{
    private SharePropertyFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IShareProperty getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IShareProperty)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("911F17D9") ,com.kingdee.eas.fdc.sellhouse.IShareProperty.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IShareProperty getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IShareProperty)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("911F17D9") ,com.kingdee.eas.fdc.sellhouse.IShareProperty.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IShareProperty getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IShareProperty)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("911F17D9"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IShareProperty getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IShareProperty)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("911F17D9"));
    }
}