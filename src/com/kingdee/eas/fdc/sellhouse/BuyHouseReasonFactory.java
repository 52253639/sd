package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BuyHouseReasonFactory
{
    private BuyHouseReasonFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IBuyHouseReason getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuyHouseReason)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("173F32B9") ,com.kingdee.eas.fdc.sellhouse.IBuyHouseReason.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IBuyHouseReason getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuyHouseReason)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("173F32B9") ,com.kingdee.eas.fdc.sellhouse.IBuyHouseReason.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IBuyHouseReason getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuyHouseReason)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("173F32B9"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IBuyHouseReason getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuyHouseReason)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("173F32B9"));
    }
}