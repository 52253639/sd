package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SellProjectFactory
{
    private SellProjectFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISellProject getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISellProject)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2FFBE5AC") ,com.kingdee.eas.fdc.sellhouse.ISellProject.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISellProject getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISellProject)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2FFBE5AC") ,com.kingdee.eas.fdc.sellhouse.ISellProject.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISellProject getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISellProject)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2FFBE5AC"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISellProject getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISellProject)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2FFBE5AC"));
    }
}