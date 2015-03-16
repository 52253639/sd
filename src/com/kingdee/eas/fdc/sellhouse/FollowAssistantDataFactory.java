package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FollowAssistantDataFactory
{
    private FollowAssistantDataFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IFollowAssistantData getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFollowAssistantData)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F45AB0BC") ,com.kingdee.eas.fdc.sellhouse.IFollowAssistantData.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IFollowAssistantData getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFollowAssistantData)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F45AB0BC") ,com.kingdee.eas.fdc.sellhouse.IFollowAssistantData.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IFollowAssistantData getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFollowAssistantData)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F45AB0BC"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IFollowAssistantData getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFollowAssistantData)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F45AB0BC"));
    }
}