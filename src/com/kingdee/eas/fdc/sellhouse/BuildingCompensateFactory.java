package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BuildingCompensateFactory
{
    private BuildingCompensateFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IBuildingCompensate getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuildingCompensate)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7E46DD26") ,com.kingdee.eas.fdc.sellhouse.IBuildingCompensate.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IBuildingCompensate getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuildingCompensate)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7E46DD26") ,com.kingdee.eas.fdc.sellhouse.IBuildingCompensate.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IBuildingCompensate getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuildingCompensate)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7E46DD26"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IBuildingCompensate getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuildingCompensate)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7E46DD26"));
    }
}