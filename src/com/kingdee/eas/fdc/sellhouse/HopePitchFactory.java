package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class HopePitchFactory
{
    private HopePitchFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IHopePitch getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IHopePitch)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0D84E2E9") ,com.kingdee.eas.fdc.sellhouse.IHopePitch.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IHopePitch getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IHopePitch)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0D84E2E9") ,com.kingdee.eas.fdc.sellhouse.IHopePitch.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IHopePitch getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IHopePitch)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0D84E2E9"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IHopePitch getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IHopePitch)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0D84E2E9"));
    }
}