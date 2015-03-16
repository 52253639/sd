package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WebMarkProcessFactory
{
    private WebMarkProcessFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IWebMarkProcess getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IWebMarkProcess)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("762CEA89") ,com.kingdee.eas.fdc.sellhouse.IWebMarkProcess.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IWebMarkProcess getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IWebMarkProcess)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("762CEA89") ,com.kingdee.eas.fdc.sellhouse.IWebMarkProcess.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IWebMarkProcess getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IWebMarkProcess)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("762CEA89"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IWebMarkProcess getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IWebMarkProcess)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("762CEA89"));
    }
}