package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FollowAssistantDataTypeFactory
{
    private FollowAssistantDataTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IFollowAssistantDataType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFollowAssistantDataType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F203F616") ,com.kingdee.eas.fdc.sellhouse.IFollowAssistantDataType.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IFollowAssistantDataType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFollowAssistantDataType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F203F616") ,com.kingdee.eas.fdc.sellhouse.IFollowAssistantDataType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IFollowAssistantDataType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFollowAssistantDataType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F203F616"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IFollowAssistantDataType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFollowAssistantDataType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F203F616"));
    }
}