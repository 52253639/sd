package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BuildingRoomEntrysFactory
{
    private BuildingRoomEntrysFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IBuildingRoomEntrys getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IBuildingRoomEntrys)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("01373A39") ,com.kingdee.eas.fdc.tenancy.IBuildingRoomEntrys.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IBuildingRoomEntrys getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IBuildingRoomEntrys)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("01373A39") ,com.kingdee.eas.fdc.tenancy.IBuildingRoomEntrys.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IBuildingRoomEntrys getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IBuildingRoomEntrys)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("01373A39"));
    }
    public static com.kingdee.eas.fdc.tenancy.IBuildingRoomEntrys getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IBuildingRoomEntrys)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("01373A39"));
    }
}