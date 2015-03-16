package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BuildingPriceSetEntryFactory
{
    private BuildingPriceSetEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IBuildingPriceSetEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuildingPriceSetEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B574A74A") ,com.kingdee.eas.fdc.sellhouse.IBuildingPriceSetEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IBuildingPriceSetEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuildingPriceSetEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B574A74A") ,com.kingdee.eas.fdc.sellhouse.IBuildingPriceSetEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IBuildingPriceSetEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuildingPriceSetEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B574A74A"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IBuildingPriceSetEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuildingPriceSetEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B574A74A"));
    }
}