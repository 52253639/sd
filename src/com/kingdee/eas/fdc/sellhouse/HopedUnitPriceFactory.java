package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class HopedUnitPriceFactory
{
    private HopedUnitPriceFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IHopedUnitPrice getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IHopedUnitPrice)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("616AEFF8") ,com.kingdee.eas.fdc.sellhouse.IHopedUnitPrice.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IHopedUnitPrice getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IHopedUnitPrice)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("616AEFF8") ,com.kingdee.eas.fdc.sellhouse.IHopedUnitPrice.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IHopedUnitPrice getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IHopedUnitPrice)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("616AEFF8"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IHopedUnitPrice getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IHopedUnitPrice)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("616AEFF8"));
    }
}