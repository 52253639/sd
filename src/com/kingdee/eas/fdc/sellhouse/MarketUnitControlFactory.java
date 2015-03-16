package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketUnitControlFactory
{
    private MarketUnitControlFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IMarketUnitControl getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMarketUnitControl)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("812147E2") ,com.kingdee.eas.fdc.sellhouse.IMarketUnitControl.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IMarketUnitControl getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMarketUnitControl)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("812147E2") ,com.kingdee.eas.fdc.sellhouse.IMarketUnitControl.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IMarketUnitControl getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMarketUnitControl)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("812147E2"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IMarketUnitControl getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMarketUnitControl)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("812147E2"));
    }
}