package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BuildingFactory
{
    private BuildingFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IBuilding getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuilding)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("159C6E8F") ,com.kingdee.eas.fdc.sellhouse.IBuilding.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IBuilding getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuilding)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("159C6E8F") ,com.kingdee.eas.fdc.sellhouse.IBuilding.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IBuilding getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuilding)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("159C6E8F"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IBuilding getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuilding)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("159C6E8F"));
    }
}