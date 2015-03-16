package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WebMarkSchemaFactory
{
    private WebMarkSchemaFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IWebMarkSchema getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IWebMarkSchema)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("526A7E47") ,com.kingdee.eas.fdc.sellhouse.IWebMarkSchema.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IWebMarkSchema getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IWebMarkSchema)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("526A7E47") ,com.kingdee.eas.fdc.sellhouse.IWebMarkSchema.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IWebMarkSchema getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IWebMarkSchema)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("526A7E47"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IWebMarkSchema getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IWebMarkSchema)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("526A7E47"));
    }
}