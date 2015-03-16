package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BuildingUnitFactory
{
    private BuildingUnitFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IBuildingUnit getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuildingUnit)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FDBCDCB3") ,com.kingdee.eas.fdc.sellhouse.IBuildingUnit.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IBuildingUnit getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuildingUnit)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FDBCDCB3") ,com.kingdee.eas.fdc.sellhouse.IBuildingUnit.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IBuildingUnit getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuildingUnit)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FDBCDCB3"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IBuildingUnit getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuildingUnit)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FDBCDCB3"));
    }
}