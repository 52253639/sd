package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BuildingFloorEntryFactory
{
    private BuildingFloorEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IBuildingFloorEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuildingFloorEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4D1D3FB5") ,com.kingdee.eas.fdc.sellhouse.IBuildingFloorEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IBuildingFloorEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuildingFloorEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4D1D3FB5") ,com.kingdee.eas.fdc.sellhouse.IBuildingFloorEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IBuildingFloorEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuildingFloorEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4D1D3FB5"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IBuildingFloorEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuildingFloorEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4D1D3FB5"));
    }
}