package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PurchaseChangeReasonFactory
{
    private PurchaseChangeReasonFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangeReason getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangeReason)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("17105910") ,com.kingdee.eas.fdc.sellhouse.IPurchaseChangeReason.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangeReason getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangeReason)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("17105910") ,com.kingdee.eas.fdc.sellhouse.IPurchaseChangeReason.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangeReason getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangeReason)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("17105910"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangeReason getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangeReason)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("17105910"));
    }
}