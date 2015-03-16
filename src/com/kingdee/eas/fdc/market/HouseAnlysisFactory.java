package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class HouseAnlysisFactory
{
    private HouseAnlysisFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IHouseAnlysis getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IHouseAnlysis)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8B48F134") ,com.kingdee.eas.fdc.market.IHouseAnlysis.class);
    }
    
    public static com.kingdee.eas.fdc.market.IHouseAnlysis getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IHouseAnlysis)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8B48F134") ,com.kingdee.eas.fdc.market.IHouseAnlysis.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IHouseAnlysis getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IHouseAnlysis)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8B48F134"));
    }
    public static com.kingdee.eas.fdc.market.IHouseAnlysis getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IHouseAnlysis)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8B48F134"));
    }
}