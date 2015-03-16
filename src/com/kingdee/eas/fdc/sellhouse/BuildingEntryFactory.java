package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BuildingEntryFactory
{
    private BuildingEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IBuildingEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuildingEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B8FD6B23") ,com.kingdee.eas.fdc.sellhouse.IBuildingEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IBuildingEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuildingEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B8FD6B23") ,com.kingdee.eas.fdc.sellhouse.IBuildingEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IBuildingEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuildingEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B8FD6B23"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IBuildingEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBuildingEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B8FD6B23"));
    }
}