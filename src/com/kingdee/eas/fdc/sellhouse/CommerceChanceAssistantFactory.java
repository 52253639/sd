package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CommerceChanceAssistantFactory
{
    private CommerceChanceAssistantFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ICommerceChanceAssistant getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICommerceChanceAssistant)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9CBC4874") ,com.kingdee.eas.fdc.sellhouse.ICommerceChanceAssistant.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ICommerceChanceAssistant getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICommerceChanceAssistant)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9CBC4874") ,com.kingdee.eas.fdc.sellhouse.ICommerceChanceAssistant.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ICommerceChanceAssistant getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICommerceChanceAssistant)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9CBC4874"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ICommerceChanceAssistant getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICommerceChanceAssistant)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9CBC4874"));
    }
}