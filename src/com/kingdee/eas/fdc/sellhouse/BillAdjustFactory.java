package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BillAdjustFactory
{
    private BillAdjustFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IBillAdjust getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBillAdjust)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("28A7B791") ,com.kingdee.eas.fdc.sellhouse.IBillAdjust.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IBillAdjust getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBillAdjust)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("28A7B791") ,com.kingdee.eas.fdc.sellhouse.IBillAdjust.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IBillAdjust getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBillAdjust)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("28A7B791"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IBillAdjust getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBillAdjust)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("28A7B791"));
    }
}