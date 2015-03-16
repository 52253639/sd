package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class KeepRoomDownFactory
{
    private KeepRoomDownFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IKeepRoomDown getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IKeepRoomDown)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DD0FEAEB") ,com.kingdee.eas.fdc.tenancy.IKeepRoomDown.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IKeepRoomDown getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IKeepRoomDown)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DD0FEAEB") ,com.kingdee.eas.fdc.tenancy.IKeepRoomDown.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IKeepRoomDown getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IKeepRoomDown)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DD0FEAEB"));
    }
    public static com.kingdee.eas.fdc.tenancy.IKeepRoomDown getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IKeepRoomDown)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DD0FEAEB"));
    }
}