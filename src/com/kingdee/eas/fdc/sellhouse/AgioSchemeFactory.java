package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AgioSchemeFactory
{
    private AgioSchemeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IAgioScheme getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAgioScheme)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("918C91AC") ,com.kingdee.eas.fdc.sellhouse.IAgioScheme.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IAgioScheme getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAgioScheme)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("918C91AC") ,com.kingdee.eas.fdc.sellhouse.IAgioScheme.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IAgioScheme getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAgioScheme)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("918C91AC"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IAgioScheme getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAgioScheme)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("918C91AC"));
    }
}