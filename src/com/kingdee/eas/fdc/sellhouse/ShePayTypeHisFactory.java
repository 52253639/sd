package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ShePayTypeHisFactory
{
    private ShePayTypeHisFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IShePayTypeHis getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IShePayTypeHis)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A3043605") ,com.kingdee.eas.fdc.sellhouse.IShePayTypeHis.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IShePayTypeHis getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IShePayTypeHis)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A3043605") ,com.kingdee.eas.fdc.sellhouse.IShePayTypeHis.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IShePayTypeHis getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IShePayTypeHis)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A3043605"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IShePayTypeHis getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IShePayTypeHis)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A3043605"));
    }
}