package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class UpdateDataFactory
{
    private UpdateDataFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IUpdateData getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IUpdateData)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2A0FE12E") ,com.kingdee.eas.fdc.sellhouse.IUpdateData.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IUpdateData getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IUpdateData)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2A0FE12E") ,com.kingdee.eas.fdc.sellhouse.IUpdateData.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IUpdateData getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IUpdateData)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2A0FE12E"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IUpdateData getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IUpdateData)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2A0FE12E"));
    }
}