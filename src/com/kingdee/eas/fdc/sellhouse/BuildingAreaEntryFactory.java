package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BuildingAreaEntryFactory
{
    private BuildingAreaEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IBuildingAreaEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuildingAreaEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("ED873A16") ,com.kingdee.eas.fdc.sellhouse.IBuildingAreaEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IBuildingAreaEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuildingAreaEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("ED873A16") ,com.kingdee.eas.fdc.sellhouse.IBuildingAreaEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IBuildingAreaEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuildingAreaEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("ED873A16"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IBuildingAreaEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuildingAreaEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("ED873A16"));
    }
}