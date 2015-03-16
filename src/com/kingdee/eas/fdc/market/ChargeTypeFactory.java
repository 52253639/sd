package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChargeTypeFactory
{
    private ChargeTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IChargeType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IChargeType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("AAF3C09F") ,com.kingdee.eas.fdc.market.IChargeType.class);
    }
    
    public static com.kingdee.eas.fdc.market.IChargeType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IChargeType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("AAF3C09F") ,com.kingdee.eas.fdc.market.IChargeType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IChargeType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IChargeType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("AAF3C09F"));
    }
    public static com.kingdee.eas.fdc.market.IChargeType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IChargeType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("AAF3C09F"));
    }
}