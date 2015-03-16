package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WebMarkFunctionFactory
{
    private WebMarkFunctionFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IWebMarkFunction getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IWebMarkFunction)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DDA92AFE") ,com.kingdee.eas.fdc.sellhouse.IWebMarkFunction.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IWebMarkFunction getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IWebMarkFunction)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DDA92AFE") ,com.kingdee.eas.fdc.sellhouse.IWebMarkFunction.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IWebMarkFunction getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IWebMarkFunction)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DDA92AFE"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IWebMarkFunction getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IWebMarkFunction)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DDA92AFE"));
    }
}