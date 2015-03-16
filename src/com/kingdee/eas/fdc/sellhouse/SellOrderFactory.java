package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SellOrderFactory
{
    private SellOrderFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISellOrder getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISellOrder)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("95F8AAA1") ,com.kingdee.eas.fdc.sellhouse.ISellOrder.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISellOrder getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISellOrder)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("95F8AAA1") ,com.kingdee.eas.fdc.sellhouse.ISellOrder.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISellOrder getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISellOrder)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("95F8AAA1"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISellOrder getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISellOrder)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("95F8AAA1"));
    }
}