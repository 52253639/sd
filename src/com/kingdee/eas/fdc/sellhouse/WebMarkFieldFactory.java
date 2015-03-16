package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WebMarkFieldFactory
{
    private WebMarkFieldFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IWebMarkField getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IWebMarkField)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4C46AB94") ,com.kingdee.eas.fdc.sellhouse.IWebMarkField.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IWebMarkField getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IWebMarkField)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4C46AB94") ,com.kingdee.eas.fdc.sellhouse.IWebMarkField.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IWebMarkField getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IWebMarkField)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4C46AB94"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IWebMarkField getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IWebMarkField)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4C46AB94"));
    }
}