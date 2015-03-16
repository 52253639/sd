package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TranStateHisFactory
{
    private TranStateHisFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ITranStateHis getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ITranStateHis)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9A4933E7") ,com.kingdee.eas.fdc.sellhouse.ITranStateHis.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ITranStateHis getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ITranStateHis)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9A4933E7") ,com.kingdee.eas.fdc.sellhouse.ITranStateHis.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ITranStateHis getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ITranStateHis)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9A4933E7"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ITranStateHis getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ITranStateHis)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9A4933E7"));
    }
}