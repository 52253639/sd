package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BuildingPriceSetFactory
{
    private BuildingPriceSetFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IBuildingPriceSet getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuildingPriceSet)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B13BF608") ,com.kingdee.eas.fdc.sellhouse.IBuildingPriceSet.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IBuildingPriceSet getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuildingPriceSet)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B13BF608") ,com.kingdee.eas.fdc.sellhouse.IBuildingPriceSet.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IBuildingPriceSet getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuildingPriceSet)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B13BF608"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IBuildingPriceSet getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuildingPriceSet)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B13BF608"));
    }
}