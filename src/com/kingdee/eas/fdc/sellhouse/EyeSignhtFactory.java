package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EyeSignhtFactory
{
    private EyeSignhtFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IEyeSignht getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IEyeSignht)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FC46DC9F") ,com.kingdee.eas.fdc.sellhouse.IEyeSignht.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IEyeSignht getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IEyeSignht)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FC46DC9F") ,com.kingdee.eas.fdc.sellhouse.IEyeSignht.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IEyeSignht getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IEyeSignht)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FC46DC9F"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IEyeSignht getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IEyeSignht)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FC46DC9F"));
    }
}