package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BuildingStructureFactory
{
    private BuildingStructureFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IBuildingStructure getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuildingStructure)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FD94CFA4") ,com.kingdee.eas.fdc.sellhouse.IBuildingStructure.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IBuildingStructure getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuildingStructure)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FD94CFA4") ,com.kingdee.eas.fdc.sellhouse.IBuildingStructure.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IBuildingStructure getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuildingStructure)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FD94CFA4"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IBuildingStructure getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuildingStructure)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FD94CFA4"));
    }
}