package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CooperateModeFactory
{
    private CooperateModeFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ICooperateMode getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICooperateMode)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D0889652") ,com.kingdee.eas.fdc.tenancy.ICooperateMode.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ICooperateMode getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICooperateMode)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D0889652") ,com.kingdee.eas.fdc.tenancy.ICooperateMode.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ICooperateMode getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICooperateMode)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D0889652"));
    }
    public static com.kingdee.eas.fdc.tenancy.ICooperateMode getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICooperateMode)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D0889652"));
    }
}