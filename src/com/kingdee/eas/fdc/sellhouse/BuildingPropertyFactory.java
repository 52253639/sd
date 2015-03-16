package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BuildingPropertyFactory
{
    private BuildingPropertyFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IBuildingProperty getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuildingProperty)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BC30B184") ,com.kingdee.eas.fdc.sellhouse.IBuildingProperty.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IBuildingProperty getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuildingProperty)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BC30B184") ,com.kingdee.eas.fdc.sellhouse.IBuildingProperty.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IBuildingProperty getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuildingProperty)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BC30B184"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IBuildingProperty getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuildingProperty)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BC30B184"));
    }
}